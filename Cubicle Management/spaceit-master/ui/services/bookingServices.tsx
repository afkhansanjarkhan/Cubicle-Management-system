import { AutoCompleteProps } from "react-native-paper-form-builder/dist/Types/Types";
import { BASE_SERVICE_URL } from "../config";

export class BookingServices{
    public async getBooking(pk:string){
        const body = {};
        return fetch(BASE_SERVICE_URL+`/booking/${pk}`,{
            method:'get',
            headers:{ "Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json' ,"Authorization":'Bearer '+localStorage.getItem("token")},
        }).then((response: any) => {
            return response.json();
        })
    }
}