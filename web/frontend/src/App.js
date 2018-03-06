import React from 'react'
import {Button} from 'belle'
import {changeR, changeX, changeY} from "./actions/actions"
import {connect} from 'react-redux'
import {bindActionCreators} from 'redux'
import Graph from "./components/parts/graph"
import Table from "./components/parts/table"
import {Col, Grid, Row} from "react-bootstrap"
import axios from "axios";
import $ from "jquery";

const urlSendPoint = "/lab4/rest/point/json/";
const urlOnDataLoad = "/lab4/rest/point/list/";
const urlLogout = "/lab4/rest/user/logout/";

class App extends React.Component{

    constructor() {
        super();
        this.state = {
            points: [ ]
        };
    }

    componentDidMount(){
        $.ajax({
            url: urlOnDataLoad,
            dataType: 'json',
            cache: false,
            success: function(data) {
                data.forEach((point) => {
                    this.state.points.push(point)
                });
                // this.setState({points: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(urlOnDataLoad, status, err.toString());
            }.bind(this)
        });
    }

    logout(){
        document.getElementById("submitHidden").click();
    }

    onCreate(newPoint) {
        // document.getElementById("xHidden").value = newPoint.x;
        // document.getElementById("yHidden").value = newPoint.y;
        // document.getElementById("radiusHidden").value = newPoint.r;
        // document.getElementById("submitHidden").click();
        axios.post(urlSendPoint, newPoint).then(() => {
            return new Promise((resolve, reject) => {
                resolve();
            });
        });
    }

    handleClick(x,y,r,inside) {
        const newPoint = {};
        newPoint['x'] = x;
        newPoint['y'] = y;
        newPoint['r'] = r;
        newPoint['inside'] = inside;
        this.onCreate(newPoint);
    }

    handleSubmit() {
        $.ajax({
            url: urlOnDataLoad,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({points: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    render() {
        return (
            <div>
                <Grid fluid>
                    <Row className="show-grid">
                        <Col xs={6} sm={12} md={6} lg={6}>
                            <Graph id="graph" points = {this.state.points} handleClick={
                                (x,y,r,inside) => {
                                    if(this.props.storeR === 0){
                                        alert("Выберити радиус");
                                        return;
                                    }
                                    this.handleClick(x,y,r,inside);
                                    this.forceUpdate();
                                    const newPoint = {};
                                    newPoint['id']= this.state.points.length;
                                    newPoint['x'] = x;
                                    newPoint['y'] = y;
                                    newPoint['r'] = r;
                                    newPoint['inside'] = inside;
                                    this.state.points.push(newPoint);
                                }} />
                        </Col>
                        <Col xs={6} sm={12} md={6} lg={6}>
                            <Table points = {this.state.points}/>
                        </Col>
                    </Row>
                    <Row className="show-grid">
                        <Col xs={12} sm={12} md={12} lg={12}>
                            <Button onClick={() =>  {
                                //document.getElementById("submitHidden").click();
                                this.logout();
                                //document.location = urlLogout;
                            }}>Выйти</Button>
                        </Col>
                    </Row>
                </Grid>
                <form method="POST" action="/lab4/rest/user/logout/" hidden="true">
                    <button type="submit" id="submitHidden"/>
                </form>
            </div>
        );
    }
}

// $(function () {
//     $('form').submit(function (e) {
//         let $form = $(this);
//         $.ajax({
//             type: $form.attr('method'),
//             url: $form.attr('action'),
//             data: $form.serialize()
//         }).done(function () {
//             console.log('success');
//         }).fail(function () {
//             console.log('fail');
//         });
//         e.preventDefault();
//     });
// });

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
    }, dispatch)
}

export default connect(mapStateToProps, matchDispatchToProps)(App);