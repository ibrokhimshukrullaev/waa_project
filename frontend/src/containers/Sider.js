import React, { useState } from "react";
import {
  DesktopOutlined,
  PieChartOutlined,
  LogoutOutlined,
  TeamOutlined,
} from "@ant-design/icons";
import { Layout, Menu } from "antd";
import { useNavigate } from "react-router";
import logo from "./logo.png";

const { Sider } = Layout;

function getItem(label, key, icon, children, onClick) {
  return {
    key,
    icon,
    children,
    label,
    onClick,
  };
}

const SideBar = ({ keycloak, initialized }) => {
  const navigate = useNavigate();
  const [collapsed, setCollapsed] = useState(false);

  let adminItems = [
    getItem("Dashboard", "1", <PieChartOutlined />, null, () => {
      navigate("/");
    }),
    getItem("Job Advertisements", "2", <DesktopOutlined />, null, () => {
      navigate("/advertisements");
    }),
    getItem("Students", "3", <TeamOutlined />, null, () => {
      navigate("/students");
    }),
    getItem("Faculty Members", "4", <TeamOutlined />, null, () => {
      navigate("/faculties");
    }),
    getItem("Logout", "5", <LogoutOutlined />, null, () => {
      keycloak.logout();
    }),
  ];

  let studentItems = [
    getItem("Dashboard", "1", <PieChartOutlined />, null, () => {
      navigate("/");
    }),
    getItem("Profile", "2", <DesktopOutlined />, null, () => {
      navigate("/profile");
    }),
    getItem(
      "Job Advertisements",
      "sub1",
      <DesktopOutlined />,
      [
        getItem("All Advertisements", "3", null, null, () => {
          navigate("/advertisements");
        }),
        getItem("My Advertisements", "4", null, null, () => {
          navigate("/my-advertisements");
        }),
      ],
      () => {}
    ),
    getItem("Logout", "5", <LogoutOutlined />, null, () => {
      keycloak.logout();
    }),
  ];
  let facultyItems = [
    getItem("Dashboard", "1", <PieChartOutlined />, null, () => {
      navigate("/");
    }),
    getItem("Profile", "2", <DesktopOutlined />, null, () => {
      navigate("/profile");
    }),
    getItem("Job Advertisements", "3", <DesktopOutlined />, null, () => {
      navigate("/advertisements");
    }),
    getItem("Students", "4", <TeamOutlined />, null, () => {
      navigate("/students");
    }),
    getItem("Logout", "5", <LogoutOutlined />, null, () => {
      keycloak.logout();
    }),
  ];

  return (
    <Sider
      collapsible
      collapsed={collapsed}
      onCollapse={(value) => setCollapsed(value)}
    >
      <div className="logo">
        <img src={logo} alt="logo" height="50px" />
      </div>
      <Menu
        theme="dark"
        defaultSelectedKeys={["1"]}
        mode="inline"
        items={
          keycloak.hasRealmRole("admin")
            ? adminItems
            : keycloak.hasRealmRole("faculty")
            ? facultyItems
            : studentItems
        }
      />
    </Sider>
  );
};

export default SideBar;
