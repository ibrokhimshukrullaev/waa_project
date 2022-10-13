import React, { useState, useEffect } from "react";
import { Form, Input, Button, message, Modal } from "antd";

import axios from "axios";
import jwtDecode from "jwt-decode";

const StudentCredentials = (props) => {
  const token = jwtDecode(props.keycloak.token);

  const [credentials, setCredentials] = useState(props.credentials);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [passwordState, setPasswordState] = useState({
    password: "",
    passwordConfirm: "",
  });

  useEffect(() => {
    setCredentials(props.credentials);
  }, [props.credentials]);

  function handleChange(e) {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  }

  const showSuccess = (text) => {
    message.success(text);
  };

  const showError = (text) => {
    message.error(text);
  };

  const onClickSave = async () => {
    const data = {
      ...credentials,
      username: token.preferred_username,
    };

    try {
      await axios.put(`${process.env.REACT_APP_BASE_URL}/users/name`, data, {
        headers: {
          // "Access-Control-Allow-Origin": "*",
          // Authorization: `Bearer  ${token}`,
        },
      });

      showSuccess("Successfully saved!!!");
    } catch (err) {
      console.error(err);
      showError("Error occurred!");
    }
  };

  async function changePassword() {
    try {
      await axios.put(
        `${process.env.REACT_APP_BASE_URL}/users/${token.preferred_username}/reset-password`,
        null,
        {
          params: {
            password: passwordState.password,
          },
        },
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            // Authorization: `Bearer  ${token}`,
          },
        }
      );

      showSuccess("Password successfully changed!!!");
      setIsModalVisible(false);
    } catch (err) {
      console.error(err);
      showError("Error occurred!");
    }
  }

  const onClickChangePassword = () => {
    setIsModalVisible(true);
  };

  function handleOk() {
    const { password, passwordConfirm } = passwordState;
    if (password === passwordConfirm) {
      changePassword();
    } else {
      showError("Passwords do not match!!!");
    }
  }

  function handleCancel() {
    setIsModalVisible(false);
  }

  function onPasswordChange(e) {
    setPasswordState({ ...passwordState, [e.target.name]: e.target.value });
  }

  const formLayout = {
    labelCol: {
      span: 8,
    },
    wrapperCol: {
      span: 24,
    },
  };

  return (
    <>
      <Form {...formLayout} layout="vertical">
        <Form.Item>
          <Input
            id="firstName"
            name="firstName"
            placeholder="First Name"
            value={credentials.firstName}
            onChange={handleChange}
          />
        </Form.Item>

        <Form.Item>
          <Input
            id="lastName"
            name="lastName"
            placeholder="Last Name"
            value={credentials.lastName}
            onChange={handleChange}
          />
        </Form.Item>

        <Form.Item>
          <Input
            id="email"
            name="email"
            placeholder="Email"
            value={credentials.email}
            onChange={handleChange}
          />
        </Form.Item>

        <Button type="primary" onClick={onClickSave}>
          Save
        </Button>
      </Form>

      <Button
        style={{ marginTop: 16 }}
        type="default"
        onClick={onClickChangePassword}
      >
        Change Password
      </Button>

      <Modal
        title="Change Password"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        width={1000}
      >
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"
        >
          <Form.Item label="New Password">
            <Input
              value={passwordState.password}
              name="password"
              onChange={onPasswordChange}
            />
          </Form.Item>

          <Form.Item label="Confirm Password">
            <Input
              value={passwordState.passwordConfirm}
              name="passwordConfirm"
              onChange={onPasswordChange}
            />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default StudentCredentials;
