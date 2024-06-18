import React from "react";
import AuthService from "../services/auth.service";

const Profile = () => {
  const currentUser = AuthService.getCurrentUser();

  const cardStyle = {
    backgroundColor: "#273849", // Navy blue color
    color: "white",
  };

  const authoritiesStyle = {
    color: "black", // Set authorities text color to black
  };

  return (
    <div className="container mt-5">
      <div className="card" style={cardStyle}>
        <div className="card-header">
          <h3 className="mb-0">
            <strong>{currentUser.username}</strong> Profile
          </h3>
        </div>
        <div className="card-body">
          <p className="card-text">
            <strong>ID:</strong> {currentUser.id}
          </p>
          <p className="card-text">
            <strong>Email:</strong> {currentUser.email}
          </p>
          <strong className="card-text">Authorities:</strong>
          <ul className="list-group mt-3" style={authoritiesStyle}>
            {Array.isArray(currentUser.roles) ? (
              currentUser.roles.map((role, index) => (
                <li className="list-group-item" key={index}>
                  {role}
                </li>
              ))
            ) : (
              <li className="list-group-item">{currentUser.roles}</li>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Profile;
