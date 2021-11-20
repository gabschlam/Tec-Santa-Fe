import React from 'react';

const Card = (props) => {
    return (
        <div className="card shadow mb-4">
            <div className="card-header py-3">
                <h6 className="text-primary font-weight-bold m-0">{props.title}</h6>
            </div>
            <div className="card-body">
                {props.children}
            </div>
        </div>  
    );
};

export default Card;