import { check } from "k6";
import encoding from "k6/encoding";
import http from "k6/http";

export default function(){
    let res = http.get("http://localhost:8080/api/v1/employee")
    check(res,{
        "is status 200": (r) => r.status ===200
    });
};