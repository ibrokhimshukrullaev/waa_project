import React, { useState, useEffect } from "react";
import { UploadOutlined } from "@ant-design/icons";
import { Form, Input, Button, Select, Upload, message } from "antd";

import jwtDecode from "jwt-decode";
import axios from "axios";

const formLayout = {
  wrapperCol: {
    span: 24,
  },
};

const majors = [
  { name: "Compro", id: 1 },
  { name: "MBA", id: 2 },
];

const StudentForm = (props) => {
  const token = jwtDecode(props.keycloak.token);

  const [componentDisabled, setComponentDisabled] = useState(false);
  const [majorId, setMajorId] = useState(props.majorId);
  const [gpa, setGpa] = useState(props.gpa);
  const [address, setAddress] = useState(props.address);
  const [file, setFile] = useState(props.file);

  const onFormLayoutChange = ({ disabled }) => {
    setComponentDisabled(disabled);
  };

  useEffect(() => {
    setMajorId(props.majorId);
    setGpa(props.gpa);
    setAddress(props.address);
    setFile(props.file);
  }, [props.majorId, props.gpa, props.address, props.file]);

  const showSuccess = (text) => {
    message.success(text);
  };

  const showError = (text) => {
    message.error(text);
  };

  const handleGpaChange = (e) => {
    setGpa(e.target.value);
  };

  const handleSelect = (value, e) => {
    setMajorId(value);
  };

  const handleAddressChange = (e) => {
    setAddress({ ...address, [e.target.name]: e.target.value });
  };

  const handleFileChange = ({ file }) => {
    if (file.status !== "uploading") {
      setFile({ file: file.originFileObj });
    }
  };

  const onClickSave = async () => {
    const formData = new FormData();
    formData.append("username", token.preferred_username);
    formData.append("departmentId", majorId);
    formData.append("gpa", gpa);
    if (file.file) formData.append("file", file.file);
    formData.append("address", JSON.stringify(address));

    try {
      await axios.put(
        `${process.env.REACT_APP_BASE_URL}/users/student/${token.preferred_username}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      showSuccess("Successfully saved!!!");
    } catch (error) {
      console.error(error);
      showError("Error occurred!");
    }
  };

  const dummyRequest = ({ file, onSuccess }) => {
    setTimeout(() => {
      onSuccess("ok");
    }, 0);
  };

  return (
    <>
      <Form
        {...formLayout}
        layout="vertical"
        onValuesChange={onFormLayoutChange}
        disabled={componentDisabled}
      >
        <Form.Item>
          <Select
            placeholder="Major"
            name="majorId"
            value={majors.find((m) => m.id === majorId)?.name}
            onSelect={handleSelect}
          >
            {majors.map((major) => {
              return (
                <Select.Option key={major.id} value={major.id}>
                  {major.name}
                </Select.Option>
              );
            })}
          </Select>
        </Form.Item>
        <Form.Item>
          <Input placeholder="GPA" value={gpa} onChange={handleGpaChange} />
        </Form.Item>

        <Form.Item label="Address">
          <Input
            placeholder="State"
            name="state"
            value={address.state}
            onChange={handleAddressChange}
          />
          <div style={{ marginTop: 8 }}>
            <Input
              placeholder="City"
              name="city"
              value={address.city}
              onChange={handleAddressChange}
            />
          </div>
          <div style={{ marginTop: 8 }}>
            <Input
              placeholder="Zip"
              name="zip"
              value={address.zip}
              onChange={handleAddressChange}
            />
          </div>
        </Form.Item>

        <Form.Item valuePropName="fileList">
          <Upload onChange={handleFileChange} customRequest={dummyRequest}>
            <Button icon={<UploadOutlined />}>Upload Resume</Button>
          </Upload>
        </Form.Item>
        <Form.Item>
          <Button type="primary" onClick={onClickSave}>
            Save
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default StudentForm;
