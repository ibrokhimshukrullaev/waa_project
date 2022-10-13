import React, { useState, useEffect } from "react";

import {
  Table,
  Layout,
  Card,
  Typography,
  Space,
  Button,
  message,
  Form,
  Input,
  Switch,
} from "antd";
import axios from "axios";
import ViewStudentModal from "../components/ViewStudentModal";

const { Title } = Typography;

const StudentsPage = ({ keycloak }) => {
  const initialFilterFormState = {
    major: null,
    city: null,
    state: null,
    name: null,
  };
  const [form] = Form.useForm();
  const [data, setData] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentStudent, setCurrentStudent] = useState({});

  const [filterFormState, setFilterFormState] = useState(
    initialFilterFormState
  );

  const actionColumn = keycloak.hasRealmRole("admin")
    ? {
        title: "Active",
        dataIndex: "active",
        key: "active",
        render: (_, student) => (
          <Space size="middle">
            <Switch
              checked={student.active}
              onChange={() => onSwitchChange(student.username)}
            />
          </Space>
        ),
      }
    : {};

  const tableColumns = [
    {
      title: "User Name",
      dataIndex: "fullname",
      key: "fullname",
    },
    {
      title: "Major",
      dataIndex: "major",
      key: "major",
    },
    {
      title: "GPA",
      dataIndex: "gpa",
      key: "gpa",
    },
    {
      title: "State",
      dataIndex: "state",
      key: "state",
    },
    {
      title: "City",
      dataIndex: "city",
      city: "city",
    },
    actionColumn,
    {
      title: "Action",
      dataIndex: "action",
      key: "action",
      render: (_, student) => (
        <Space size="middle">
          <Button type="primary" onClick={() => onClickView(student)}>
            View
          </Button>
        </Space>
      ),
    },
  ];

  function getTableData() {
    return data.map((student) => {
      return {
        key: student.id,
        fullname: student.firstName + " " + student.lastName,
        username: student.username,
        major: student.major.name,
        gpa: student.gpa,
        cv: student.cv,
        state: student.address.state,
        city: student.address.city,
        active: student.active,
      };
    });
  }

  const showError = (text) => {
    message.error(text);
  };

  const onSwitchChange = async (username) => {
    try {
      await axios.put(
        `${process.env.REACT_APP_BASE_URL}/users/${username}/active`
      );
      fetchStudents();
    } catch (e) {
      console.error(e);
    }
  };

  async function fetchStudents() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BASE_URL}/users/student`,
        {
          headers: {
            // Authorization: `Bearer ${keycloak.token}`
          },
        }
      );
      if (res.data) {
        setData(res.data);
      }
    } catch (err) {
      console.error(err);
      showError(err);
    }
  }

  function handleFilterFormChange(e) {
    setFilterFormState({ ...filterFormState, [e.target.name]: e.target.value });
  }

  const filterStudents = async () => {
    const response = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/users/student/filter`,
      { params: filterFormState }
    );
    setData(
      response.data.map((student, idx) => ({
        key: idx + 1,
        ...student,
      }))
    );
  };

  const onClickSearch = () => {
    filterStudents();
    setFilterFormState(initialFilterFormState);
  };

  useEffect(() => {
    fetchStudents();
  }, []);

  const onClickView = (student) => {
    setCurrentStudent(student);
    showModal();
  };

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <>
      <ViewStudentModal
        student={currentStudent}
        keycloak={keycloak}
        isVisible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
      />
      <Layout className="site-layout">
        <Card
          style={{ margin: "50px" }}
          extra={
            <Form layout="inline" form={form}>
              <Form.Item>
                <Input
                  placeholder="State"
                  name="state"
                  value={filterFormState.state}
                  onChange={handleFilterFormChange}
                />
              </Form.Item>
              <Form.Item>
                <Input
                  placeholder="City"
                  name="city"
                  value={filterFormState.city}
                  onChange={handleFilterFormChange}
                />
              </Form.Item>
              <Form.Item>
                <Input
                  placeholder="Major"
                  name="major"
                  value={filterFormState.companyName}
                  onChange={handleFilterFormChange}
                />
              </Form.Item>
              <Form.Item>
                <Input
                  placeholder="Name"
                  name="name"
                  value={filterFormState.name}
                  onChange={handleFilterFormChange}
                />
              </Form.Item>
              <Form.Item>
                <Button type="primary" onClick={() => onClickSearch()}>
                  Search
                </Button>
              </Form.Item>
            </Form>
          }
        >
          <Title style={{ textAlign: "center" }}>Students</Title>
          <Table
            style={{ marginTop: "32px", border: "1px solid #f0f0f0" }}
            columns={tableColumns}
            dataSource={getTableData()}
            size="large"
          />
        </Card>
      </Layout>
    </>
  );
};

export default StudentsPage;
