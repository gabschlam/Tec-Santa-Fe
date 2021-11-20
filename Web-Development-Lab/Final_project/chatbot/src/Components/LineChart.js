import React from 'react'
import { Line } from 'react-chartjs-2'

function LineChart(){
    const data = {
        labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        datasets: [
            {
                label: 'Crecimiento 2020',
                data: [290, 294, 306, 405, 459, 580, 584, 595, 626, 639, 702, 705],
                borderColor: ['rgb(20, 141, 219, 0.2)'],
                backgroundColor: ['rgb(20, 141, 219, 0.2)'],
                pointBackgroundColor: ['rgb(20, 141, 219, 0.2)'],
                pointBorderColor: ['rgb(20, 141, 219, 0.2)']

            }
        ]
    }
    return <Line data={data}/>
}
export default LineChart