import { check } from "k6";
import encoding from "k6/encoding";
import http from "k6/http";

export default function(){
    let res = http.get("http://127.")
}