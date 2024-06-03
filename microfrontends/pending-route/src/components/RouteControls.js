import React from 'react';

const RouteControls = ({ currentSelectedRouteId, acceptRoute, denyRoute }) => {
  return (
    <div id="selectedRouteControls" className={currentSelectedRouteId ? 'active' : ''}>
      <button className="pendingBtn" onClick={acceptRoute}>Accept Changes</button>
      <button className="pendingBtn" onClick={denyRoute}>Deny Changes</button>
    </div>
  );
};

export default RouteControls;
