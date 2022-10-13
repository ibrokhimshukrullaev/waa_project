import React, { useState, useEffect } from "react";
import { List, Avatar, Modal, Upload } from "antd";
import { Button, Form, Input } from "antd";
import { UserOutlined } from "@ant-design/icons";
import TextArea from "antd/lib/input/TextArea";

import axios from "axios";
import jwtDecode from "jwt-decode";

const ViewStudentModal = ({ student, keycloak, isVisible, onOk, onCancel }) => {
  const address = student.address ?? {};
  const token = jwtDecode(keycloak.token);

  const [comments, setComments] = useState([]);
  const [componentDisabled, setComponentDisabled] = useState(true);
  const [newComment, setNewComment] = useState("");

  async function fetchComments() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BASE_URL}/comments/${student.username}`,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );
      if (res.data) {
        setComments(res.data);
      } else {
        setComments([]);
      }
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    fetchComments();
  }, []);

  const onFormLayoutChange = ({ disabled }) => {
    setComponentDisabled(disabled);
  };

  const onTypeComment = (e) => {
    setNewComment(e.target.value);
  };

  const onAddComment = async () => {
    let commentData = {
      studentUsername: student.username,
      facultyUsername: token.preferred_username,
      comment: newComment,
    };

    try {
      let res = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/comments`,
        commentData,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );

      if (res.data) {
        setComments(comments.concat(res.data));
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <Modal
        title="User Info"
        visible={isVisible}
        onOk={onOk}
        onCancel={onCancel}
        width={1000}
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
          onValuesChange={onFormLayoutChange}
          disabled={componentDisabled}
        >
          <Form.Item label="User Name">
            <Input value={student.username} />
          </Form.Item>
          <Form.Item label="Major">
            <Input value={student.major} />
          </Form.Item>
          <Form.Item label="GPA">
            <Input value={student.gpa} />
          </Form.Item>
          <Form.Item label="City">
            <Input value={address.city} />
          </Form.Item>
          <Form.Item label="State">
            <Input value={address.state} />
          </Form.Item>
          <Form.Item label="Zip">
            <Input value={address.zip} />
          </Form.Item>
          <Form.Item label="CV">
            <Upload
              fileList={[
                {
                  uid: "1",
                  name: "cv",
                  status: "done",
                  url: `${student.cv}`,
                },
              ]}
            />
          </Form.Item>
        </Form>

        {keycloak.hasRealmRole("faculty") ? (
          <>
            <List
              style={{
                height: 150,
                overflow: "auto",
                margin: "16px 0px",
              }}
              size="small"
              header={<div>Comments</div>}
              bordered
              dataSource={comments}
              renderItem={(item) => (
                <div>
                  <List.Item>
                    <List.Item.Meta
                      avatar={<Avatar shape="square" icon={<UserOutlined />} />}
                      title={item.comment}
                      description={item.facultyUsername}
                    />
                  </List.Item>
                </div>
              )}
            />
            <Form name="basic">
              <Form.Item name="comment">
                <TextArea value={newComment} onChange={onTypeComment} />
              </Form.Item>

              <Form.Item>
                <Button type="primary" htmlType="submit" onClick={onAddComment}>
                  Add Comment
                </Button>
              </Form.Item>
            </Form>
          </>
        ) : (
          <></>
        )}
      </Modal>
    </>
  );
};

export default ViewStudentModal;
