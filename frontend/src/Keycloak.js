import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:8081",
  realm: "waa-project",
  clientId: "waa-client",
  "enable-cors": true,
  //"ssl-required": "external",
  // "public-client": true,
});

export default keycloak;
