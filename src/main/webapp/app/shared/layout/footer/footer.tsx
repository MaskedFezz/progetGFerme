// Footer.jsx

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content" style={{ backgroundColor: '#28a745', color: '#fff', padding: '20px 0' }}>
    <Row>
      <Col md="12" className="text-center">
        <p>
          <Translate contentKey="home.footer.copyright">© Copyright Mohamed Fezzazi and Ghazi Yassine</Translate>
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
