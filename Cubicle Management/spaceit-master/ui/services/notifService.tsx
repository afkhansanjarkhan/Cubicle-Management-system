import { BASE_SERVICE_URL } from '../config';

export default class NotifService {
  constructor() { }

  public async search() {
    const body = {};
    const responseData = await fetch(BASE_SERVICE_URL + '/notification', {
       method: 'get',
       headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
     }).then((response: any) => {
       console.log(response.headers);
       return response.json();
     }); 

    return responseData;
  }

  public async unReadFlagUpdate(uniqueObjIdString?: any, pk?: any) {
    var body = {};
    const resposeData = await fetch(BASE_SERVICE_URL + `/notification/markread/${pk}`, {
      method: 'put',
      body: JSON.stringify(body),
      headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
    }).then((response: any) => {
      console.log(response.headers);
      return response.json();
    });

    return {};
  }

  public async deleteNotification(uniqueObjIdString?: any, pk?: any) {
    var body = {
      moduleName: 'admin',
      appCommand: {
        actionCommandName: 'performAction',
        appCommandParams: [
          { name: 'PObjModelString', value: 'NotifMsg' },
          { name: 'ActionType', value: 'DELETE' },
        ],
      },
      objectIdentifiers: [
        {
          objectType: 'NotifMsg',
          mgrId: 50461,
          verNum: 0,
          dateUpdated: null,
          uniqueObjIdString: uniqueObjIdString,
          pk: pk,
        },
      ],
    };

    const resposeData = await fetch(BASE_SERVICE_URL + `/notification/${pk}`, {
      method: 'DELETE',
      body: JSON.stringify(body),
      headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
    }).then((response: any) => {
      console.log(response.headers);
      return response.data;
    });


    return resposeData;
  }
}
