// import React, { useState, useEffect } from "react";
// import RegularUserService from "../services/RegularUserService";

// const NotificationReceiver = () => {
//   const [notifications, setNotifications] = useState([]);
//   const [error, setError] = useState("");

//   useEffect(() => {
//     const fetchNotifications = async () => {
//       try {
//         const notifications = await RegularUserService.receiveNotifications();
//         setNotifications(notifications);
//       } catch (err) {
//         setError("Error fetching notifications.");
//       }
//     };

//     fetchNotifications();
//   }, []);

//   return (
//     <div className="container mt-5">
//       <h2>Notifications</h2>
//       {error && <div className="alert alert-danger">{error}</div>}
//       {notifications.length > 0 ? (
//         <ul>
//           {notifications.map((notification, index) => (
//             <li key={index}>{notification}</li>
//           ))}
//         </ul>
//       ) : (
//         <p>No notifications</p>
//       )}
//     </div>
//   );
// };

// export default NotificationReceiver;
