import React, { useEffect, useState } from "react";
import { Layout } from "antd";
import { Table, Tag, Space, Card, Form, Input, Button, Select } from "antd";
import ViewJobAdModal from "../components/ViewJobAdModal";
import axios from "axios";
const { Header } = Layout;

const Advertisements = ({ keycloak }) => {
  const initialFilterFormState = {
    companyName: null,
    city: null,
    state: null,
    tags: [],
  };
  const [form] = Form.useForm();
  const [advertisements, setAdvertisements] = useState([]);
  const [currentAdvertisement, setCurrentAdvertisement] = useState({});
  const [filterFormState, setFilterFormState] = useState(
    initialFilterFormState
  );
  const [tagsList, setTagsList] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);

  function handleFilterFormChange(e) {
    setFilterFormState({ ...filterFormState, [e.target.name]: e.target.value });
  }

  const onTagSelectChange = (value) => {
    setFilterFormState({ ...filterFormState, tags: value });
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

  const fetchAdvertisements = async () => {
    const response = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/all/${keycloak.tokenParsed.preferred_username}`
    );
    setAdvertisements(
      response.data.map((advertisement, idx) => ({
        key: idx + 1,
        ...advertisement,
        tags: advertisement.tags.map((tag) => tag.name),
      }))
    );
  };

  const filterAdvertisements = async () => {
    const response = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/filter`,
      {
        params: {
          ...filterFormState,
          tags: filterFormState.tags.map((tag) => `${tag}`).join(","),
        },
      }
    );
    setAdvertisements(
      response.data.map((advertisement, idx) => ({
        key: idx + 1,
        ...advertisement,
        tags: advertisement.tags.map((tag) => tag.name),
      }))
    );
  };

  const onClickSearch = () => {
    filterAdvertisements();
    setFilterFormState(initialFilterFormState);
  };

  useEffect(() => {
    fetchAdvertisements();
    fetchTags();
  }, []);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  const onClickView = (advertisement) => {
    setCurrentAdvertisement(advertisement);
    showModal();
  };

  const onClickApply = (advertisement) => {
    handleApplyClick(advertisement.id);
  };

  const handleApplyClick = async (advertisementId) => {
    await axios.post(`${process.env.REACT_APP_BASE_URL}/job-applications`, {
      jobAdvertisementId: advertisementId,
      studentUsername: keycloak.tokenParsed.preferred_username,
    });
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
          {keycloak.hasRealmRole("student") && (
            <Button type="primary" onClick={() => onClickApply(advertisement)}>
              Apply
            </Button>
          )}
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
        isEditable={false}
        onOk={handleOk}
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
          title="All Advertisements"
          extra={
            <Form layout="inline" form={form}>
              <Form.Item>
                <Input
                  placeholder="Company Name"
                  name="companyName"
                  value={filterFormState.companyName}
                  onChange={handleFilterFormChange}
                />
              </Form.Item>
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
              <Form.Item style={{ minWidth: "180px" }}>
                <Select
                  placeholder="Select tag"
                  mode="multiple"
                  onChange={onTagSelectChange}
                >
                  {tagsList.map((tag) => {
                    return (
                      <Select.Option key={tag.id} value={tag.id}>
                        {tag.name}
                      </Select.Option>
                    );
                  })}
                </Select>
              </Form.Item>
              <Form.Item>
                <Button type="primary" onClick={() => onClickSearch()}>
                  Search
                </Button>
              </Form.Item>
            </Form>
          }
        >
          <Table columns={jobAdColumns} dataSource={advertisements} />
        </Card>
      </Layout>
    </>
  );
};

export default Advertisements;
