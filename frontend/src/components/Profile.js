import React from "react";
import AuthService from "../services/auth.service";
 
const Profile = () => {
  const currentUser = AuthService.getCurrentUser();
 
  return (
    <div className="container mt-5">
      <div className="card">
        <div className="card-header">
          <h3 className="mb-0">
            <strong>{currentUser.username}</strong> Profile
          </h3>
        </div>
        <div className="card-body">
          <p className="card-text">
            <strong>Id:</strong> {currentUser.id}
          </p>
          <p className="card-text">
            <strong>Email:</strong> {currentUser.email}
          </p>
          <strong>Authorities:</strong>
          <ul className="list-group mt-3">
            {Array.isArray(currentUser.role) ? (
              currentUser.role.map((role, index) => (
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