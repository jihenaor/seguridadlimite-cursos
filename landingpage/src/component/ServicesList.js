import React from 'react';
import { Row } from 'reactstrap';
import ServiceItem from './ServiceItem';

const ServicesList = ({ services, toggleShowMore }) => (
    <Row className="mt-1 pt-1">
        {services.map((service, key) => (
            <ServiceItem service={service} toggleShowMore={toggleShowMore} key={key} />
        ))}
    </Row>
);

export default ServicesList;
