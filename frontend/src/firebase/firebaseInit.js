import firebase from "firebase/app";
import "firebase/messaging";

const firebaseConfig = {
  apiKey: "AIzaSyAf23GZC_EpSSWOzT0RkCd1M9r3KLfIOfg",
  authDomain: "waa-alumni.firebaseapp.com",
  projectId: "waa-alumni",
  storageBucket: "waa-alumni.appspot.com",
  messagingSenderId: "463035630086",
  appId: "1:463035630086:web:0cb41682982732dc1e7987",
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

const publicKey =
  "BKuRpJZUpn9EIt3tM2lUNbA3sM_zpXI8sNY1oKNpNU9C1pX2e6ptrE6ebJhEjsNNsBHjEam6ShAFvlj8rqXjM1M";

export const getPrivateToken = async (setTokenFound) => {
  let currentToken = "";

  try {
    currentToken = await messaging.getToken({ vapidKey: publicKey });
    if (currentToken) {
      setTokenFound(true);
    } else {
      setTokenFound(false);
    }
  } catch (error) {
    console.log("An error occurred while retrieving token. ", error);
  }

  return currentToken;
};

export const onMessageListener = () =>
  new Promise((resolve) => {
    messaging.onMessage((payload) => {
      resolve(payload);
    });
  });
