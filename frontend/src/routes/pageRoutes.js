import React, { useEffect, useState } from "react";
import { Route, Routes } from "react-router";
import { Spin, Layout } from "antd";
import UserRoleModal from "../components/UserRoleModal";
import axios from "axios";

import Dashboard from "../pages/Dashboard";
import ProfilePage from "../pages/ProfilePage";
import StudentsPage from "../pages/StudentsPage";
import FacultiesPage from "../pages/FacultiesPage";
import Advertisements from "../pages/Advertisements";
import MyAdvertisements from "../pages/MyAdvertisements";

const PageRoutes = ({ keycloak, initialized }) => {
  // const [user, setUser] = useState({});
  const [isModalVisible, setIsModalVisible] = useState(false);
  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = (role) => {
    updateRole(role);
    setIsModalVisible(false);
  };

  const updateRole = async (role) => {
    try {
      const res = await axios.post(
        `${process.env.REACT_APP_BASE_URL}/users/role`,
        {
          username: keycloak.idTokenParsed.preferred_username,
          role: role,
        }
      );
      console.log(res);
    } catch (error) {
      console.error(error);
    }
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };
  useEffect(() => {
    if (initialized) {
      if (
        !keycloak.hasRealmRole("admin") &&
        !keycloak.hasRealmRole("student") &&
        !keycloak.hasRealmRole("faculty")
      ) {
        showModal();
      }
    }
  }, [keycloak, initialized]);

  if (!initialized) {
    return (
      <Layout className="site-layout">
        <Spin
          delay="5000ms"
          size="large"
          style={{
            display: "flex",
            marginTop: "30%",
            justifyContent: "center",
          }}
        />
      </Layout>
    );
  }
  return (
    <>
      <UserRoleModal
        isVisible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
      />
      <Routes>
        <Route path="/" element={<Dashboard keycloak={keycloak} />} />
        <Route path="/profile" element={<ProfilePage keycloak={keycloak} />} />
        <Route
          path="/students"
          element={<StudentsPage keycloak={keycloak} />}
        />
        <Route
          path="/advertisements"
          element={<Advertisements keycloak={keycloak} />}
        />
        <Route
          path="/my-advertisements"
          element={<MyAdvertisements keycloak={keycloak} />}
        />
        <Route
          path="/faculties"
          element={<FacultiesPage keycloak={keycloak} />}
        />
      </Routes>
    </>
  );
};

export default PageRoutes;
