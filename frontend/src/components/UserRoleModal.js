import { Button, Form, Select, Modal } from "antd";
import React, { useState } from "react";

const UserRoleModal = ({ isVisible, onOk, onCancel }) => {
  const [componentSize, setComponentSize] = useState("default");
  const [role, setRole] = useState("");

  const onFormLayoutChange = ({ size }) => {
    setComponentSize(size);
  };

  const onRoleSelectChange = (value) => {
    setRole(value);
  };
  return (
    <>
      <Modal
        title="User Role"
        visible={isVisible}
        onOk={() => onOk(role)}
        onCancel={onCancel}
      >
        <Form
          labelCol={{
            span: 4,
          }}
          wrapperCol={{
            span: 14,
          }}
          layout="horizontal"
          initialValues={{
            size: componentSize,
          }}
          onValuesChange={onFormLayoutChange}
          size={componentSize}
        >
          <Form.Item label="Choose role">
            <Select onChange={onRoleSelectChange}>
              <Select.Option value="student">student</Select.Option>
              <Select.Option value="faculty">faculty</Select.Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default UserRoleModal;
