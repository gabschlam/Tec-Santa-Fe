import React from 'react';

const Status = () => {
    return (
        <div className="row">
        <div className="col-lg-6 mb-4">
            <div className="card text-white bg-primary shadow">
                <div className="card-body">
                    <p className="m-0">705 Seguidores</p>
                    <p className="text-white-50 small m-0">Twitter</p>
                </div>
            </div>
        </div>
        <div className="col-lg-6 mb-4">
            <div className="card text-white bg-success shadow">
                <div className="card-body">
                    <p className="m-0">3,562 Respuestas</p>
                    <p className="text-white-50 small m-0">Whatsapp</p>
                </div>
            </div>
        </div>
        <div className="col-lg-6 mb-4">
            <div className="card text-white bg-warning shadow">
                <div className="card-body">
                    <p className="m-0">223 Me gusta</p>
                    <p className="text-white-50 small m-0">Facebook</p>
                </div>
            </div>
        </div>
        <div className="col-lg-6 mb-4">
            <div className="card text-white bg-danger shadow">
                <div className="card-body">
                    <p className="m-0">8,123 Seguidores</p>
                    <p className="text-white-50 small m-0">Instagram</p>
                </div>
            </div>
        </div>
    </div>
    );
};

export default Status;