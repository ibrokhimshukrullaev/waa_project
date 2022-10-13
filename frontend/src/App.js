import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./Keycloak";
import { Provider } from "react-redux";
import { store } from "./redux/store";
import { onMessageListener } from "./firebase/firebaseInit";
import AppRouter from "./containers/Container";
import { useState } from "react";
import { notification } from "antd";

function App() {
  const [notificationState, setNotificationState] = useState({
    title: "",
    body: "",
  });

  function openNotification() {
    notification.open({
      message: notificationState.title,
      description: notificationState.body,
      onClick: () => {
        console.log("Notification Clicked!");
      },
    });
  }

  onMessageListener()
    .then((payload) => {
      setNotificationState({
        title: payload.notification.title,
        body: payload.notification.body,
      });
      setTimeout(() => {
        openNotification();
      }, 2000);
    })
    .catch((err) => console.log("failed: ", err));
  return (
    <div>
      <Provider store={store}>
        <ReactKeycloakProvider
          initOptions={{ onLoad: "login-required", promiseType: "native" }}
          authClient={keycloak}
        >
          <AppRouter />
        </ReactKeycloakProvider>
      </Provider>
    </div>
  );
}

export default App;
