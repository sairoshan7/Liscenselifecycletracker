import React from 'react';
import officeImage from '../assets/LLTIMAGE.webp'; // Importing the office image
import llt2example from '../assets/llt2example.png'; // Importing the llt2example image

const AboutUs = () => {
  return (
    <section className="about-us-section mt-5 light-grey">
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <img src={officeImage} alt="Office" className="img-office animate-scroll" />
          </div>
          <div className="col-md-8 animate-scroll">
            <div>
              <h2 className="text-primary">About Us</h2>
              <p className="lead text-muted">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquet, velit sed fermentum sollicitudin, sem felis rutrum massa, eget fringilla velit est quis odio. Nulla eget lectus id risus sagittis auctor ac vel nisi. Ut et lorem auctor, lobortis mi non, pharetra tellus. Curabitur nec fermentum ligula. Donec sed metus orci. Nullam pharetra euismod ex, ac suscipit elit aliquet sit amet. Phasellus varius bibendum est, non efficitur ligula.
              </p>
              <p className="lead text-muted">
                Proin imperdiet, velit ac vehicula interdum, nunc urna bibendum lacus, eu tincidunt sem nisi in ex. Sed auctor neque elit, non feugiat libero finibus id. Nam ullamcorper purus ut lacus maximus, vitae vestibulum neque faucibus. In efficitur volutpat arcu id elementum. Mauris sit amet urna lorem.
              </p>
            </div>
          </div>
        </div>
      </div>
      <div className="container mt-5">
        <div className="row">
          <div className="col-md-8 animate-scroll">
            <div>
              <h2 className="text-primary">Software Management</h2>
              <p className="lead text-muted">
                Software management involves overseeing the installation, maintenance, and licensing of software applications within an organization. It includes tasks such as tracking license usage, ensuring compliance with software agreements, and optimizing software utilization to minimize costs.
              </p>
            </div>
          </div>
          <div className="col-md-4">
            <img src={llt2example} alt="LLT2 Example" className="img-fluid mb-3 rounded animate-scroll" />
            {/* Additional content or image related to software management can be added here */}
          </div>
        </div>
      </div>
    </section>
  );
};

export default AboutUs;
