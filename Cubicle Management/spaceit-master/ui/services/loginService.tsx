import { BASE_SERVICE_URL } from '../config';

export default class LoginService {

  public async login(username: string, password: string) {
    // commenting the login apis for now.Lets enable when spring boot authentication apis are ready
    const body = { username, password };
     return fetch( BASE_SERVICE_URL + '/login', {
       method: 'post',
       body: JSON.stringify(body),
       headers: { 'Content-Type': 'application/json' },
     }).then(async (response: any) => {
       console.log(response.headers);
       return response.json();
     })
    // returning dummy data 

  }

  public async bootstrap(){
    return fetch(BASE_SERVICE_URL + '/bootstrap', {
      method: 'GET',
      headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
    }).then((response: any) => {
      return response.json();
    });
  }
  
  public async logout() { }
}