import React, { useEffect, useState } from "react";
import { Layout, Typography } from "antd";
import { Row, Card } from "antd";
import StudentCredentials from "../components/StudentCredentials";
import StudentForm from "../components/StudentForm";
import JobHistory from "../components/JobHistory";
import FacultyForm from "../components/FacultyForm";
import axios from "axios";
import jwtDecode from "jwt-decode";

const { Title } = Typography;

const ProfilePage = ({ keycloak }) => {
  const token = jwtDecode(keycloak.token);

  const [studentCredentials, setStudentCredentials] = useState({
    email: "",
    firstName: "",
    lastName: "",
  });
  const [majorId, setMajorId] = useState();
  const [gpa, setGpa] = useState("");
  const [address, setAddress] = useState({
    state: "",
    city: "",
    zip: "",
  });
  const [file, setFile] = useState({
    url: "",
    file: null,
  });

  function isStudent() {
    return keycloak.hasRealmRole("student");
  }

  async function fetchStudentDetails() {
    let res = await axios.get(
      `${process.env.REACT_APP_BASE_URL}/users/student/${token.preferred_username}`,
      {
        headers: {
          //todo auth
        },
      }
    );

    console.log(res.data);

    if (res.data) {
      setMajorId(res.data.major.id);
      setGpa(res.data.gpa);
      setAddress(res.data.address);
      setFile({ url: res.data.cv });

      setStudentCredentials({
        email: res.data.email,
        firstName: res.data.firstName,
        lastName: res.data.lastName,
      });
    }
  }

  useEffect(() => {
    if (isStudent()) {
      fetchStudentDetails();
    }
  }, []);

  if (isStudent()) {
    return (
      <Layout className="site-layout">
        <Row
          style={{
            display: "grid",
            gridTemplateColumns: "50% 50%",
            padding: "50px",
          }}
        >
          <Card span={12} style={{ padding: "0 50px" }}>
            <Title style={{ textAlign: "center" }}>Details</Title>
            <StudentCredentials
              keycloak={keycloak}
              credentials={studentCredentials}
            />
          </Card>

          <Card span={12} style={{ padding: "0 50px" }}>
            <Title style={{ textAlign: "center" }}>Extra Details</Title>
            <StudentForm
              keycloak={keycloak}
              majorId={majorId}
              gpa={gpa}
              address={address}
              file={file}
            />
          </Card>
        </Row>

        <Card style={{ margin: "50px" }}>
          <Title style={{ textAlign: "center" }}>Job History</Title>
          <JobHistory keycloak={keycloak} />
        </Card>
      </Layout>
    );
  } else {
    return (
      <Layout className="site-layout">
        <Card style={{ margin: "50px 100px" }}>
          <Title style={{ textAlign: "center" }}>Faculty</Title>
          <div
            style={{
              width: "50%",
              margin: "0 auto",
            }}
          >
            <FacultyForm keycloak={keycloak} />
          </div>
        </Card>
      </Layout>
    );
  }
};

export default ProfilePage;
