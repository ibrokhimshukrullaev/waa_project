import React, { useState, useEffect } from "react";
import { Table, Button, Tag } from "antd";
import AddJobExperienceModal from "./AddJobExperienceModal";

import axios from "axios";
import jwtDecode from "jwt-decode";

const JobHistory = ({ keycloak }) => {
  const columns = [
    {
      title: "ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Company Name",
      dataIndex: "companyName",
      key: "companyName",
    },
    {
      title: "Start Date",
      dataIndex: "startDate",
      key: "startDate",
    },
    {
      title: "End Date",
      dataIndex: "endDate",
      key: "endDate",
    },
    {
      title: "Reason To Leave",
      dataIndex: "reasonToLeave",
      key: "reasonToLeave",
    },
    {
      title: "Tags",
      dataIndex: "tags",
      key: "tags",
      render: (_, { tags }) => (
        <>
          {tags.map((tag, index) => {
            let color = tag.name.length > 5 ? "geekblue" : "green";
            return (
              <Tag color={color} key={index}>
                {tag.name}
              </Tag>
            );
          })}
        </>
      ),
    },
    {
      title: "Action",
      dataIndex: "action",
      key: "action",
      render: (_, { id }) => (
        <>
          <Button danger onClick={() => deleteHistory(id)}>
            Delete
          </Button>
        </>
      ),
    },
  ];

  const token = jwtDecode(keycloak.token);
  const [tagsList, setTagsList] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [jobHistories, setJobHistories] = useState([]);

  async function fetchJobHistories() {
    try {
      let res = await axios.get(
        `${process.env.REACT_APP_BASE_URL}/job-histories/`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (res.data) {
        const tableData = res.data.map((data) => {
          return { ...data, key: data.id };
        });
        setJobHistories(tableData);
      }
    } catch (error) {
      console.error(error);
    }
  }

  async function deleteHistory(id) {
    let res = await axios.delete(
      `${process.env.REACT_APP_BASE_URL}/job-histories/` + id,
      {
        headers: {
          //todo auth
        },
      }
    );
    fetchJobHistories();
  }

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
    fetchTags();
    fetchJobHistories();
  }, []);

  function formatDate(date) {
    var d = new Date(date),
      month = "" + (d.getMonth() + 1),
      day = "" + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2) month = "0" + month;
    if (day.length < 2) day = "0" + day;
    return [year, month, day].join("-");
  }

  const showModal = () => {
    setIsModalVisible(true);
  };

  const addJobExperience = async (jobHistory, historyDates) => {
    let jsonData = {
      companyName: jobHistory.companyName,
      startDate: formatDate(historyDates.startDate),
      endDate: formatDate(historyDates.endDate),
      reasonToLeave: jobHistory.reasonToLeave,
      tagIds: jobHistory.tags,
    };

    try {
      let res = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/job-histories/${token.preferred_username}`,
        jsonData,
        {
          headers: {
            // "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            // Authorization: `Bearer  ${keycloak.token}`,
          },
        }
      );
      fetchJobHistories();
      setIsModalVisible(false);
    } catch (error) {
      console.error(error);
    }
  };

  const hideModal = () => {
    setIsModalVisible(false);
  };

  return (
    <>
      <Table
        style={{ border: "1px solid #f0f0f0" }}
        columns={columns}
        dataSource={jobHistories}
        size="large"
      />
      <Button type="primary" style={{ marginTop: "16px" }} onClick={showModal}>
        Add Job Experience
      </Button>

      <AddJobExperienceModal
        isModalVisible={isModalVisible}
        tags={tagsList}
        hideModal={hideModal}
        addJobExperience={addJobExperience}
      />
    </>
  );
};

export default JobHistory;
