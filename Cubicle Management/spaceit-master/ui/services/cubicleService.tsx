import { AutoCompleteProps } from "react-native-paper-form-builder/dist/Types/Types";
import { BASE_SERVICE_URL } from "../config";

export default class CubicleService {

  public async create(cubicleId: string, locationName: string,status: string, ) {
        const body = { cubicleId,locationName, status,};
         return fetch( BASE_SERVICE_URL + '/cubicle', {
           method: 'post',
           body: JSON.stringify({
            "cubicleId":cubicleId,
            "location":{"locationName":locationName},
            "status":status
           }),
           headers: { "Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json' ,"Authorization":'Bearer '+localStorage.getItem("token")},
         }).then((response: any) => {
           console.log(response.headers);
           return response.json();
         })
        }

        public async search() {
          const body = {};
           const responseData = await fetch(BASE_SERVICE_URL + '/cubicle', {
             method: 'get',
             headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
           }).then((response: any) => {
             console.log(response.headers);
             return response.json();
           }); 
          return responseData;
        }
        public async fetchCubicle(pk: string) {
          //use this to fetch more details  after notification is opened
          const body = {};
           return fetch(BASE_SERVICE_URL + `/cubicle/${pk}`, {
             method: 'get',
            //  body: JSON.stringify(body),
             headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
            //  headers: { 'Content-Type': 'application/json' },
           }).then((response: any) => {
             return response.json();
             
          });


        //   dummmy data till booking apis are completed
        //   return [
        //     { "name": "Booking ID", "value": 123 },
        //   { "name": "Name", "value": "Pavan" },
        //   { "name": "Email", "value": "pavan@mymail.com" },
        //   { "name": "Booked for", "value": "12/12/2022" },
        //   { "name": "Cubicle Id", "value": "W009" },
        //   { "name": "Status", "value": "Pending Approval" }
        //   ];
        }
}
