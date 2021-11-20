import React from 'react';
import { Doughnut } from 'react-chartjs-2';

const Chart = () => {
    const data = {
        labels: ['Whatsapp', 'Facebook', 'Instagram', 'Twitter'],
        datasets: [
            {
                label: 'Crecimiento 2020',
                data: [290, 294, 306, 405, 459,],
                borderColor: ['rgb(20, 141, 219, 0.2)'],
                backgroundColor: ['rgb(20, 141, 219, 0.2)'],
                pointBackgroundColor: ['rgb(20, 141, 219, 0.2)'],
                pointBorderColor: ['rgb(20, 141, 219, 0.2)']

            }
        ]
    }
    return (
        <div className="card shadow">
            <div className="card-header d-flex justify-content-between align-items-center">
                <h6 className="text-primary font-weight-bold m-0">Revenue Sources</h6>
                <div className="dropdown no-arrow">
                    <button className="btn btn-link btn-sm dropdown-toggle" data-toggle="dropdown" aria-expanded="false" type="button"><i className="fas fa-ellipsis-v text-gray-400"></i></button>
                    <div className="dropdown-menu shadow dropdown-menu-right animated--fade-in">
                        <p className="text-center dropdown-header">dropdown header:</p>
                        <button className="dropdown-item" href="">&nbsp;Action</button>
                        <button className="dropdown-item" href="">&nbsp;Another action</button>
                        <div className="dropdown-divider"></div>
                        <button className="dropdown-item" href="">&nbsp;Something else here</button>
                    </div>
                </div>
            </div>
            <div className="card-body">
                <Doughnut data={data} />
            </div>
        </div>
    );
};

export default Chart;