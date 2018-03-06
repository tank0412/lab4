import React from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import {changeX, changeY, changeR} from "../../actions/actions"
import {Button} from "belle"
import InputNumber from 'rc-input-number'

class Input extends React.Component{
    constructor(props) {
        super(props);
        //this.handleChangeR = this.handleChangeR.bind(this);
        //this.handleChangeX = this.handleChangeX.bind(this);
    }


    handleSubmit(e) {
        e.preventDefault();
    }

    handleChangeX(value){
        this.props.changeX(value);
    }

    handleChangeR(value){
        this.props.changeR(value);
    }

    render() {
        let newPoint = {};
        newPoint['x'] = this.props.storeX;
        newPoint['y'] = this.props.storeY;
        newPoint['r'] = this.props.storeR;
        newPoint['inside'] = this.props.inside;

        let inputs = [
            <div id="divX">
                <label>Значение X</label><br/>
                <label>-5</label><input name="checkboxX" type="radio" value="-5" onClick={() => this.props.changeX(-5)}/>
                <label>-4</label><input name="checkboxX" type="radio" value="-4" onClick={() => this.props.changeX(-4)}/>
                <label>-3</label><input name="checkboxX" type="radio" value="-3" onClick={() => this.props.changeX(-3)}/>
                <label>-2</label><input name="checkboxX" type="radio" value="-2" onClick={() => this.props.changeX(-2)}/>
                <label>-1</label><input name="checkboxX" type="radio" value="-1" onClick={() => this.props.changeX(-1)}/>
                <label>0</label><input name="checkboxX" type="radio" value="0" onClick={() => this.props.changeX(0)}/>
                <label>1</label><input name="checkboxX" type="radio" value="1" onClick={() => this.props.changeX(1)}/>
                <label>2</label><input name="checkboxX" type="radio" value="2" onClick={() => this.props.changeX(2)}/>
                <label>3</label><input name="checkboxX" type="radio" value="3" onClick={() => this.props.changeX(3)}/>
            </div>,
            <div id="divY">
                <label>Значение Y</label><br/>
                <InputNumber placeholder="Введите Y" className="rounded" defaultValue={this.props.storeY}
                             min={-3} max={3}
                             onChange={(value) => {
                                 if(value < -3 || value > 3) {
                                     alert("invalid value");
                                     return;
                                 }
                                 this.props.changeY(value)
                             }} />
            </div>,
            <div id="divR">
                <label>Значение R</label><br/>
                <label>1</label><input name="checkboxR" type="radio" value="1" onClick={() => {this.props.changeR(1)}}/>
                <label>2</label><input name="checkboxR" type="radio" value="2" onClick={() => this.props.changeR(2)}/>
                <label>3</label><input name="checkboxR" type="radio" value="3" onClick={() => this.props.changeR(3)}/>
                <label>4</label><input name="checkboxR" type="radio" value="4" onClick={() => this.props.changeR(4)}/>
                <label>5</label><input name="checkboxR" type="radio" value="5" onClick={() => this.props.changeR(5)}/>
            </div>,
            <Button className="rounded" key="submit" onClick=
                {  (e) => {
                    this.handleSubmit(e);
                    let inside = this.props.check(this.props.storeX,this.props.storeY,this.props.storeR);
                    this.props.handleClick(this.props.storeX,this.props.storeY,this.props.storeR,inside);
                }
                }>Проверить</Button>,
        ];

        return (
            <form>
                {inputs}
            </form>
        )
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
    return bindActionCreators(
        {
            changeX: changeX,
            changeY: changeY,
            changeR: changeR,
        },
        dispatch)
}

export default connect(mapStateToProps, matchDispatchToProps)(Input);