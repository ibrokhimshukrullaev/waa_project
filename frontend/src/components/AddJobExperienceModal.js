import React, { useState } from "react";

import { Form, Input, Select, DatePicker, Modal } from "antd";

const { TextArea } = Input;

const AddJobExperienceModal = ({
  isModalVisible,
  tags,
  hideModal,
  addJobExperience,
}) => {
  const dateFormat = "YYYY-MM-DD";

  const [jobHistory, setJobHistory] = useState({
    companyName: "",
    reasonToLeave: "",
  });

  const [historyDates, setHistoryDates] = useState({
    startDate: "",
    endDate: "",
    tags: [],
  });

  const onJobChange = (e) => {
    setJobHistory({ ...jobHistory, [e.target.name]: e.target.value });
  };

  const onJobSelectChange = (value) => {
    setJobHistory({ ...jobHistory, tags: value });
  };

  const onStartDateChange = (e) =>
    setHistoryDates({ ...historyDates, startDate: e });

  const onEndDateChange = (e) =>
    setHistoryDates({ ...historyDates, endDate: e });

  const handleOk = () => {
    addJobExperience(jobHistory, historyDates);
    hideModal();
  };

  const handleCancel = () => {
    hideModal();
  };

  return (
    <>
      <Modal
        title="Add Job Experience"
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
          <Form.Item label="Company Name">
            <Input
              value={jobHistory.companyName}
              name="companyName"
              onChange={onJobChange}
            />
          </Form.Item>
          <Form.Item label="Start Date">
            <DatePicker
              name="startDate"
              value={historyDates.startDate}
              onChange={onStartDateChange}
              format={dateFormat}
            />
          </Form.Item>
          <Form.Item label="End Date">
            <DatePicker
              name="endDate"
              value={historyDates.endDate}
              onChange={onEndDateChange}
              format={dateFormat}
            />
          </Form.Item>

          <Form.Item label="Reason to leave">
            <TextArea
              rows={4}
              name="reasonToLeave"
              value={jobHistory.reasonToLeave}
              onChange={onJobChange}
            />
          </Form.Item>

          <Form.Item label="Tags">
            <Select
              placeholder="Select tag"
              mode="multiple"
              onChange={onJobSelectChange}
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
        </Form>
      </Modal>
    </>
  );
};

export default AddJobExperienceModal;
