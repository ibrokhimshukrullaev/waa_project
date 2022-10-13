import React, { useEffect, useState } from "react";
import { Layout } from "antd";
import ReactECharts from "echarts-for-react";
import * as echarts from "echarts";
import { Table, Tag, Card } from "antd";
import axios from "axios";

import {
  DatasetComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
} from "echarts/components";
import { BarChart } from "echarts/charts";
import { CanvasRenderer } from "echarts/renderers";
const { Header } = Layout;
echarts.use([
  DatasetComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  BarChart,
  CanvasRenderer,
]);

const Dashboard = () => {
  const [recentAdvertisements, setRecentAdvertisements] = useState([]);
  const [topAdvertisements, setTopAdvertisements] = useState([]);
  const [adsAndAppsCount, setAdsAndAppsCount] = useState([]);
  const [adsCountByTag, setAdsCountByTag] = useState([]);
  const [studentsCountByState, setStudentsCountByState] = useState([]);
  const [studentsCountByCity, setStudentsCountByCity] = useState([]);

  const fetchAdvertisements = async () => {
    const recentAds = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/recent`
    );

    const topAds = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/top`
    );

    const adsAndAppsCountResponse = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/count-by-address`
    );

    const adsCountByTagResponse = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/job-advertisements/count-by-tag`
    );

    const studentsCountByStateResponse = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/users/student/count-by-state`
    );

    const studentsCountByCityResponse = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/users/student/count-by-city`
    );

    setRecentAdvertisements(
      recentAds.data.map((advertisement, idx) => ({
        key: idx + 1,
        ...advertisement,
        tags: advertisement.tags.map((tag) => tag.name),
      }))
    );
    setTopAdvertisements(
      topAds.data.map((advertisement, idx) => ({
        key: idx + 1,
        ...advertisement,
        tags: advertisement.tags.map((tag) => tag.name),
      }))
    );
    setAdsAndAppsCount(adsAndAppsCountResponse.data);
    setAdsCountByTag(adsCountByTagResponse.data);
    setStudentsCountByState(studentsCountByStateResponse.data);
    setStudentsCountByCity(studentsCountByCityResponse.data);
  };

  useEffect(() => {
    fetchAdvertisements();
  }, []);

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
  ];

  var jobAdOption = {
    legend: {},
    tooltip: {},
    dataset: {
      source: [
        ["address", ...adsAndAppsCount.map((adCount) => adCount.address)],
        [
          "Job Advertisements",
          ...adsAndAppsCount.map((adCount) => adCount.jobAdsCount),
        ],
        [
          "Job Applications",
          ...adsAndAppsCount.map((adCount) => adCount.jobAppsCount),
        ],
      ],
    },
    xAxis: [{ type: "category", gridIndex: 0 }],
    yAxis: [{ gridIndex: 0 }],
    grid: [{ bottom: "20%" }, { top: "70%" }],
    series: [
      // These series are in the first grid.
      { type: "bar", seriesLayoutBy: "row" },
      { type: "bar", seriesLayoutBy: "row" },
    ],
  };

  var chartOptionByState = {
    xAxis: {
      type: "category",
      data: studentsCountByState.map((c) => c.state),
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: studentsCountByState.map((c) => c.count),
        type: "bar",
      },
    ],
  };

  var chartOptionByCity = {
    xAxis: {
      type: "category",
      data: studentsCountByCity.map((c) => c.city),
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: studentsCountByCity.map((c) => c.count),
        type: "line",
      },
    ],
  };

  var chartOptionByTags = {
    title: {
      text: "Job Advertisements",
      left: "center",
    },
    tooltip: {
      trigger: "item",
    },
    legend: {
      orient: "vertical",
      left: "left",
    },
    series: [
      {
        name: "Access From",
        type: "pie",
        radius: "50%",
        data: adsCountByTag.map((ad) => ({
          value: ad.count,
          name: ad.tag,
        })),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  };

  return (
    <Layout className="site-layout">
      <Header
        className="site-layout-background"
        style={{
          padding: 0,
        }}
      />
      <ReactECharts style={{ background: "#fff" }} option={jobAdOption} />
      <ReactECharts
        style={{ background: "#fff" }}
        option={chartOptionByState}
      />
      <ReactECharts style={{ background: "#fff" }} option={chartOptionByCity} />
      <ReactECharts style={{ background: "#fff" }} option={chartOptionByTags} />
      <Card style={{ width: "100%" }} title="Most Recent Job Advertisements">
        <Table
          columns={jobAdColumns}
          dataSource={recentAdvertisements}
          pagination={false}
        />
      </Card>
      <Card style={{ width: "100%" }} title="Most Applied Job Advertisements">
        <Table
          columns={jobAdColumns}
          dataSource={topAdvertisements}
          pagination={false}
        />
      </Card>
    </Layout>
  );
};

export default Dashboard;
