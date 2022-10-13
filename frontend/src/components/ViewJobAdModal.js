import { Modal, Form, Input, Select, Upload, Button } from "antd";
import React, { useState } from "react";
import { UploadOutlined } from "@ant-design/icons";
const { TextArea } = Input;

const ViewJobAdModal = ({
  advertisement,
  tags,
  isVisible,
  isEditable,
  onOk,
  onCancel,
}) => {
  const [componentDisabled] = useState(!isEditable);
  const [modalState, setModalState] = useState({});

  const handleFileChange = ({ file }) => {
    if (file.status !== "uploading") {
      setModalState({ ...modalState, file: file.originFileObj });
    }
  };

  const onTagSelectChange = (value) => {
    setModalState({ ...modalState, tags: value });
  };

  function handleModalChange(e) {
    setModalState({ ...modalState, [e.target.name]: e.target.value });
  }

  const dummyRequest = ({ file, onSuccess }) => {
    setTimeout(() => {
      onSuccess("ok");
    }, 0);
  };

  return (
    <>
      <Modal
        title="Job Advertisement"
        visible={isVisible}
        onOk={() => (isEditable ? onOk(modalState) : onOk)}
        onCancel={onCancel}
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
          disabled={componentDisabled}
        >
          <Form.Item label="Company">
            <Input
              defaultValue={advertisement?.companyName}
              value={modalState.companyName}
              name="companyName"
              onChange={handleModalChange}
            />
          </Form.Item>
          <Form.Item label="Benefits">
            <Input
              defaultValue={advertisement?.benefits}
              value={modalState.benefits}
              name="benefits"
              onChange={handleModalChange}
            />
          </Form.Item>
          <Form.Item label="State">
            <Input
              defaultValue={advertisement?.state}
              value={modalState.state}
              onChange={handleModalChange}
              name="state"
            />
          </Form.Item>
          <Form.Item label="City">
            <Input
              defaultValue={advertisement?.city}
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
              defaultValue={advertisement?.description}
              value={modalState.description}
              name="description"
              onChange={handleModalChange}
            />
          </Form.Item>
          <Form.Item valuePropName="fileList">
            {isEditable ? (
              <Upload onChange={handleFileChange} customRequest={dummyRequest}>
                <Button icon={<UploadOutlined />}>Upload File</Button>
              </Upload>
            ) : (
              <Upload
                fileList={[
                  {
                    uid: "1",
                    name: "file",
                    status: "done",
                    url: `${advertisement.file}`,
                  },
                ]}
              />
            )}
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default ViewJobAdModal;
