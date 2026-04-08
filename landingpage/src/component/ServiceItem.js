import React from 'react';
import { Col, Link } from 'reactstrap';

const ServiceItem = ({ service, toggleShowMore, key }) => (
    <Col lg={4} key={key}>
        <div className="services-box p-3 mt-4">

            <div style={{ display: 'flex', alignItems: 'center', minHeight: '170px' }}>

                <div className="services-icon bg-soft-primary" style={{ marginRight: '50px' }}>
                    <img src={`../assets/images/services/${item.id}.webp`}
                        alt={item.id} style={{ width: '84px', height: '84px', borderRadius: "50%" }} />
                </div>

                <h5>{item.title}</h5>
            </div>
            {
                item.description.length > 0 ? (
                    <p className="text-muted mt-3">{item.description}</p>
                ) : null
            }

            <div className="services-detail-box p-1 mt-4">

                <div className="mt-2">
                    <ul>
                        {item.text?.map((text, index) => (
                            <li key={index * 10} className="text-light mt-3">{text}</li>
                        ))}
                    </ul>
                </div>

                <div className="mt-3">
                    <Link
                        to="#"
                        className="f-16"
                        style={{ color: "#000" }}
                        onClick={() => this.toggleShowMore(key)}
                    >
                        {item.showMore ? 'Leer menos' : 'Leer más...'}
                        <i className={` mdi ${item.showMore ? 'mdi-minus' : 'mdi-arrow-right'} ml-1`}></i>
                    </Link>

                </div>

                {item.showMore && (
                    <div className="mt-3">
                        <ul>
                            {item.expansiveText.map((text, index) => (
                                <li key={index} className="text-light mt-3">{text}</li>
                            ))}
                        </ul>
                    </div>
                )}

            </div>

        </div>

    </Col>
);

export default ServiceItem;
