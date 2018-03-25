import React from 'react'
import {change, changeR, changeX, changeY} from "../../actions/actions"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import Input from "./input"

class Graph extends React.Component {
    constructor(props) {
        super(props);
        this.array = props.points;
        this.handleClickInGraph = this.props.handleClick;
    }

    componentDidMount() {
        this.updateCanvas(this.props.storeR);
        this.drawPoints(this.props.storeR)
    }

    componentDidUpdate() {
        this.updateCanvas(this.props.storeR);
        this.drawPoints(this.props.storeR);
    }

    getCursorPosition(event){
        const step = 50;
        const canvas = document.getElementById("canvas");
        //let ctx = canvas.getContext('2d');
        let coords = canvas.getBoundingClientRect();
        let x = event.pageX - coords.left - window.pageXOffset;
        let y = event.pageY - coords.top - window.pageYOffset;

        x = ((x - canvas.width / 2) / step);
        y = ((canvas.width / 2 - y) / step);
        return {x: x, y: y};
    }

    check(x,y,radius) {
        return (x <= 0 && x >= radius*(-1) && y >= radius*(-1) && y <= (x+radius)/2)
            || (x>=0 && y<=0 && (x*x + y*y) <= radius*radius/4)
    }

    drawPoints(r) {
        let pointArray = this.array;
        const canvas = document.getElementById('canvas');
        const ctx = canvas.getContext('2d');
        const step = 50;
        pointArray.forEach(function(entry) {
            if(entry.r === r)
            {
                if(entry.inside) ctx.fillStyle = '#5FE16B';
                else ctx.fillStyle = 'red';
            }
            else ctx.fillStyle = '#D2B6B0';

            let x = entry.x;
            let y = entry.y;
            x = x * step + canvas.width / 2;
            y = (y * step - canvas.width / 2) * -1;

            ctx.beginPath();
            ctx.arc(x, y, 4, 0, 2* Math.PI);
            ctx.fill();
            ctx.closePath();
        });
    }

    drawAPoint(x,y,r,inside) {
        const step = 50;

        const canvas = document.getElementById('canvas');
        const ctx = canvas.getContext('2d');
        if(inside) ctx.fillStyle = '#5FE16B';
        else ctx.fillStyle = 'red';

        x = x * step + canvas.width / 2;
        y = (y * step - canvas.width / 2) * -1;

        ctx.beginPath();
        ctx.arc(x, y, 4, 0, 2* Math.PI);
        ctx.fill();
        ctx.closePath();
    }

    updateCanvas(radius) {
        const canvas = document.getElementById('canvas');
        const size = 250;
        canvas.width = size * 2;
        canvas.height = size * 2;
        radius *= 50;
        const ctx = canvas.getContext('2d');

        //Отрисовка фигуры
        ctx.fillStyle = '#6A5ACD';

        ctx.fillRect(size-radius/2 , size, radius/2, radius);

        ctx.beginPath();
        ctx.moveTo(size - radius, size);
        ctx.lineTo(size, size);
        ctx.lineTo(size, size - radius/2);
        ctx.lineTo(size - radius, size);
        ctx.fill();

        ctx.arc(size, size, radius/2, 0, 1.5*Math.PI, true); // круг
        ctx.fill();

        //Отрисовка осей
        ctx.beginPath();
        ctx.moveTo(0, size);
        ctx.lineTo(2*size, size);
        ctx.moveTo(size, 0);
        ctx.lineTo(size, 2*size);
        ctx.stroke();
        ctx.closePath();
        this.drawPoints(radius);
    }

    render() {
        return (
            <div>
                <canvas id="canvas" onClick={(event) => {
                    if(this.props.storeR === 0) {
                        alert("Выберите радиус");
                        return;
                    }
                    let arr = this.getCursorPosition(event);
                    let inside = this.check(arr['x'], arr['y'], this.props.storeR);
                    this.drawAPoint(arr['x'],arr['y'],this.props.storeR, inside);
                    this.handleClickInGraph(arr['x'], arr['y'], this.props.storeR, inside);

                }} ref="canvas" width={300} height={300}/>

                <Input check={this.check} handleClick={ (x,y,r,inside) => {
                    this.drawAPoint(x,y,r,inside);
                    this.handleClickInGraph(x,y,r,inside); }} />
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        storeX: state.point.x,
        storeY: state.point.y,
        storeR: state.point.r,
        inside: state.point.inside,
    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        changeX: changeX,
        changeY: changeY,
        changeR: changeR,
        change: change,
    }, dispatch)
}

export default connect(mapStateToProps, matchDispatchToProps)(Graph);