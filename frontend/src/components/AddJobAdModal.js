import { Button, Modal, Form, Input, Select, Upload, message } from "antd";
import React, { useState } from "react";
import { UploadOutlined } from "@ant-design/icons";
import axios from "axios";
const { TextArea } = Input;

const AddJobAdModal = ({ tags, keycloak }) => {
  const [modalState, setModalState] = useState({
    companyName: "",
    benefits: "",
    description: "",
    state: "",
    city: "",
    createdBy: "",
    tags: [],
  });
  const [file, setFile] = useState(null);

  const handleFileChange = ({ file }) => {
    if (file.status !== "uploading") {
      setFile({ file: file.originFileObj });
    }
  };

  const showSuccess = (text) => {
    message.success(text);
  };

  const showError = (text) => {
    message.error(text);
  };

  const onTagSelectChange = (value) => {
    setModalState({ ...modalState, tags: value });
  };

  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = async () => {
    const formData = new FormData();
    formData.append("createdBy", keycloak.tokenParsed.preferred_username);
    formData.append("companyName", modalState.companyName);
    formData.append("description", modalState.description);
    formData.append("benefits", modalState.benefits);
    formData.append("state", modalState.state);
    formData.append("city", modalState.city);
    if (file.file) formData.append("file", file.file);
    formData.append("tagIds", modalState.tags);
    try {
      await axios.post(
        `${process.env.REACT_APP_BASE_URL}/job-advertisements`,
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
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const dummyRequest = ({ file, onSuccess }) => {
    setTimeout(() => {
      onSuccess("ok");
    }, 0);
  };

  function handleModalChange(e) {
    setModalState({ ...modalState, [e.target.name]: e.target.value });
  }

  return (
    <>
      <Button type="primary" onClick={showModal}>
        Add
      </Button>
      <Modal
        title="Job Advertisement"
        visible={isModalVisible}
        onOk={() => handleOk()}
        onCancel={handleCancel}
      >
        <Form
          style={{
            display: "grid",
            gridTemplateColumns: "50% 50%",
            columnGap: "10px",
          }}
          labelCol={{ span: 24 }}
          wrapperCol={{ span: 24 }}
          layout="vertical"
        >
          <Form.Item label="Company">
            <Input
              value={modalState.companyName}
              name="companyName"
              onChange={handleModalChange}
            />
          </Form.Item>
          <Form.Item label="Benefits">
            <Input
              value={modalState.benefits}
              name="benefits"
              onChange={handleModalChange}
            />
          </Form.Item>
          <Form.Item label="State">
            <Input
              value={modalState.state}
              onChange={handleModalChange}
              name="state"
            />
          </Form.Item>
          <Form.Item label="City">
            <Input
              value={modalState.city}
              onChange={handleModalChange}
              name="city"
            />
          </Form.Item>
          <Form.Item label="Tags">
            <Select
              placeholder="Select tag"
              mode="multiple"
              onChange={onTagSelectChange}
            >
              {tags.map((tag) => {
                return (
                  <Select.Option key={tag.id} value={tag.id}>
                    {tag.name}
                  </Select.Option>
                );
              })}
            </Select>
          </Form.Item>
          <Form.Item label="Description">
            <TextArea
              rows={4}
              value={modalState.description}
              name="description"
              onChange={handleModalChange}
            />
          </Form.Item>
          <Form.Item valuePropName="fileList">
            <Upload onChange={handleFileChange} customRequest={dummyRequest}>
              <Button icon={<UploadOutlined />}>Upload File</Button>
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default AddJobAdModal;
