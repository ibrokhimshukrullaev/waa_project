import React from "react";
import SideBar from "./Sider";
import PageRoutes from "../routes/pageRoutes";
import { BrowserRouter } from "react-router-dom";
import "./container.css";
import "antd/dist/antd.css";
import { Layout } from "antd";
import { useKeycloak } from "@react-keycloak/web";

const AppRouter = () => {
  const { keycloak, initialized } = useKeycloak();
  return (
    <BrowserRouter>
      <Layout
        style={{
          minHeight: "100vh",
        }}
      >
        <SideBar keycloak={keycloak} initialized={initialized} />
        <PageRoutes keycloak={keycloak} initialized={initialized} />
      </Layout>
    </BrowserRouter>
  );
};

export default AppRouter;
