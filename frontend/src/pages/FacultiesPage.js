import React, { useState, useEffect } from "react";
import { Table, Layout, Card, Typography, message, Switch, Space } from "antd";
import axios from "axios";

const { Title } = Typography;

const FacultiesPage = ({ keycloak }) => {
  const actionColumn = keycloak.hasRealmRole("admin")
    ? {
        title: "Active",
        dataIndex: "active",
        key: "active",
        render: (_, faculty) => (
          <Space size="middle">
            <Switch
              checked={faculty.active}
              onChange={() => onSwitchChange(faculty.username)}
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
      title: "Department",
      dataIndex: "department",
      key: "department",
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
    {
      title: "Zip",
      dataIndex: "zip",
      city: "zip",
    },
    actionColumn,
  ];
  const [data, setData] = useState([]);

  const showError = (text) => {
    message.error(text);
  };

  const onSwitchChange = async (username) => {
    try {
      await axios.put(
        `${process.env.REACT_APP_BASE_URL}/users/${username}/active`
      );
      fetchFaculties();
    } catch (e) {
      console.error(e);
    }
  };

  async function fetchFaculties() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BASE_URL}/users/faculty`,
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

  useEffect(() => {
    fetchFaculties();
  }, []);

  function getTableData() {
    return data.map((faculty) => {
      return {
        key: faculty.id,
        fullname: faculty.firstName + " " + faculty.lastName,
        username: faculty.username,
        department: faculty.department.name,
        state: faculty.address.state,
        city: faculty.address.city,
        zip: faculty.address.zip,
        active: faculty.active,
      };
    });
  }

  return (
    <>
      <Layout className="site-layout">
        <Card style={{ margin: "50px" }}>
          <Title style={{ textAlign: "center" }}>Faculty Members</Title>
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

export default FacultiesPage;
