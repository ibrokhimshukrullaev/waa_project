import React, { useEffect, useState } from "react";

import { Form, Input, Button, Select, message } from "antd";
import axios from "axios";
import jwtDecode from "jwt-decode";

const formLayout = {
  wrapperCol: {
    span: 24,
  },
};

const majors = [
  { name: "Compro", id: 1 },
  { name: "MBA", id: 2 },
];

const FacultyForm = ({ keycloak }) => {
  const token = jwtDecode(keycloak.token);

  const [majorId, setMajorId] = useState();
  const [address, setAddress] = useState({
    state: "",
    city: "",
    zip: "",
  });

  const showSuccess = (text) => {
    message.success(text);
  };

  const showError = (text) => {
    message.error(text);
  };

  async function fetchFacultyData() {
    let res = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/users/faculty/${token.preferred_username}`,
      {
        headers: {
          //todo auth
        },
      }
    );
    if (res.data) {
      setMajorId(res.data.department.id);
      setAddress(res.data.address);
    }
  }

  useEffect(() => {
    fetchFacultyData();
  }, []);

  const handleChange = (e) => {
    setAddress({ ...address, [e.target.name]: e.target.value });
  };

  const handleSelect = (value, e) => {
    setMajorId(value);
  };

  const onClickSave = async () => {
    try {
      let facultyData = {
        username: token.preferred_username,
        departmentId: majorId,
        address: address,
      };

      await axios.put(
        `${process.env.REACT_APP_BASE_URL}/users/faculty/${token.preferred_username}`,
        facultyData,
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            // Authorization: `Bearer  ${keycloak.token}`,
          },
        }
      );
      showSuccess("Successfully saved!!!");
    } catch (error) {
      console.error(error);
      showError("Error occurred!");
    }
  };

  return (
    <>
      <Form {...formLayout} layout="vertical">
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

        <Form.Item label="Address">
          <Input
            placeholder="State"
            name="state"
            value={address.state}
            onChange={handleChange}
          />
          <div style={{ marginTop: 8 }}>
            <Input
              placeholder="City"
              name="city"
              value={address.city}
              onChange={handleChange}
            />
          </div>
          <div style={{ marginTop: 8 }}>
            <Input
              placeholder="Zip"
              name="zip"
              value={address.zip}
              onChange={handleChange}
            />
          </div>
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

export default FacultyForm;
