import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="farm-management-container">
      <Row>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>
        <Col md="9">
          <div className="edgy-box">
            <h1 className="display-4">
              <Translate contentKey="home.title">Welcome,Farm management</Translate>
            </h1>
            <p className="lead">
              <Translate contentKey="home.subtitle">This is your Farm</Translate>
            </p>
          </div>
          {account?.login ? (
            <div>
              <Alert color="success">
                <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                  You are logged in as user {account.login}.
                </Translate>
              </Alert>
            </div>
          ) : (
            <div>
              <Button tag={Link} to="/login" color="primary" size="lg" style={{ marginTop: '100px' }}>
                {' '}
                {/* Sign In Button */}
                <Translate contentKey="global.messages.info.authenticated.link">Sign In</Translate>
              </Button>
              <Button tag={Link} to="/account/register" color="success" size="lg" style={{ marginTop: '100px' }}>
                {' '}
                {/* Register Button */}
                <Translate contentKey="global.messages.info.register.link">Register</Translate>
              </Button>
            </div>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default Home;
