import React, { useEffect, useState } from "react";
import { Layout } from "antd";
import { Table, Tag, Space, Button, Card, message } from "antd";
import ViewJobAdModal from "../components/ViewJobAdModal";
import axios from "axios";
import AddJobAdModal from "../components/AddJobAdModal";
const { Header } = Layout;

const MyAdvertisements = ({ keycloak }) => {
  const [myAds, setMyAds] = useState([]);
  const [tagsList, setTagsList] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentAdvertisement, setCurrentAdvertisement] = useState({});

  const fetchMyAdvertisements = async () => {
    const response = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/${keycloak.tokenParsed.preferred_username}`
    );
    setMyAds(
      response.data.map((advertisement, idx) => ({
        key: idx + 1,
        ...advertisement,
        tags: advertisement.tags.map((tag) => tag.name),
        files: [],
      }))
    );
  };

  async function fetchTags() {
    let res = await axios.get(`${process.env.REACT_APP_BASE_URL}/tags`, {
      headers: {
        //todo auth
      },
    });
    if (res.data) {
      setTagsList(res.data);
    }
  }

  useEffect(() => {
    fetchMyAdvertisements();
    fetchTags();
  }, []);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleUpdate = async (advertisement) => {
    const formData = new FormData();
    formData.append("createdBy", keycloak.tokenParsed.preferred_username);
    formData.append("description", advertisement.description);
    formData.append("benefits", advertisement.benefits);
    formData.append("state", advertisement.state);
    formData.append("city", advertisement.city);
    formData.append("file", advertisement.file);
    formData.append("tagIds", advertisement.tags);
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
      setIsModalVisible(false);
      showSuccess("Successfully saved!!!");
      fetchMyAdvertisements();
    } catch (err) {
      console.error(err);
      showError("Error occurred!");
    }

    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const onClickView = (advertisement) => {
    setCurrentAdvertisement(advertisement);
    showModal();
  };

  const handleDelete = async (advertisement) => {
    await axios.delete(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/${advertisement.id}`
    );
    await fetchMyAdvertisements();
  };

  const showSuccess = (text) => {
    message.success(text);
  };

  const showError = (text) => {
    message.error(text);
  };

  const jobAdColumns = [
    {
      title: "Nº",
      dataIndex: "key",
      key: "key",
    },
    {
      title: "Company",
      dataIndex: "companyName",
      key: "companyName",
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "Created By",
      dataIndex: "createdBy",
      key: "createdBy",
    },
    {
      title: "State",
      dataIndex: "state",
      key: "state",
    },
    {
      title: "City",
      dataIndex: "city",
      key: "city",
    },
    {
      title: "Tags",
      key: "tags",
      dataIndex: "tags",
      render: (_, { tags }) => (
        <>
          {tags.map((tag) => {
            let color = tag.length > 5 ? "geekblue" : "green";
            return (
              <Tag color={color} key={tag}>
                {tag.toUpperCase()}
              </Tag>
            );
          })}
        </>
      ),
    },
    {
      title: "Action",
      key: "action",
      render: (_, advertisement) => (
        <Space size="middle">
          <Button type="primary" onClick={() => onClickView(advertisement)}>
            View
          </Button>
          <Button
            type="primary"
            danger
            onClick={() => handleDelete(advertisement)}
          >
            Delete
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <>
      <ViewJobAdModal
        advertisement={currentAdvertisement}
        tags={tagsList}
        isVisible={isModalVisible}
        isEditable={true}
        onOk={handleUpdate}
        onCancel={handleCancel}
      />
      <Layout className="site-layout">
        <Header
          className="site-layout-background"
          style={{
            padding: 0,
          }}
        />
        <Card
          style={{ width: "100%" }}
          title="My Advertisements"
          extra={<AddJobAdModal tags={tagsList} keycloak={keycloak} />}
        >
          <Table
            columns={jobAdColumns}
            dataSource={myAds}
            // onChange={handleTableChange}
          />
        </Card>
      </Layout>
    </>
  );
};

export default MyAdvertisements;
