import { BASE_SERVICE_URL } from '../config';

export default class SearchService {
    constructor() { }
    public async search(obj: string) {
        const responseData = await fetch(BASE_SERVICE_URL + '/' + obj,  {
            method: 'GET',
            mode: 'cors',
            headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
                           
            /*headers: { "Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json' ,"Authorization":'Bearer '+localStorage.getItem("token")},*/
        }).then((response: any) => {
            console.log(response);
            console.log(response.headers);
            return response.json();
        });
        return responseData;
    }
}
