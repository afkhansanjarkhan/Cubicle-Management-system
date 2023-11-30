import { any, number, object } from "prop-types";
import { useEffect, useState } from "react";
import { Item } from "react-native-paper/lib/typescript/components/List/List";
import { exp } from "react-native-reanimated";

export const mapNotifications = (data: any) => {
  const result: any = [];
  data.forEach((item: any, index: number) => {
    if (item) {
      result.push(mapNotification(item, index));
    }
  });
  return result;
};

export const mapCubicles = (data: any) => {
  const result: any = [];
  data.forEach((item: any, index: number) => {
    if (item) {
      result.push(mapCubicle(item, index));
    }
  });
  return result;
};

export const mapBookings = (data: any) => {
  console.log(data);
  const result: any = [];
  data.forEach((item: any, index: number) => {
    if (item) {
      result.push(mapBooking(item, index));
      console.log(item);
    }
  });
  return result;
};

export const mapDemos = (data: any) => {
  const result: any = [];
  data.forEach((item: any, index: number) => {
    if (item) {
      result.push(mapDemo(item, index));
    }
  });
  return result;
};

export const mapDemo = (item: any, index: number) => {
  return {
    key: `${index}`,
    title: item['name'] + ' email is ' + item['email'],
    details: item['dateCreated'],
    objectIdentifier: item['id'],
    pk: item['id'],
  };
};

export const mapNotification = (item: any, index: number) => {
  return {
    key: `${index}`,
    title: item['title'],
    details: item['description'],
    notifyType:item['notifyType'],
    unread: !item['read'],
    objectIdentifier: item['obj'],
    pk: item['id'],
  };
};

export const mapCubicle = (item: any, index: number) => {
  return {
    key: `${index}`,
    cubicleId: item['cubicleId'],
    status: item['status'],
    pk: item['id'],
  };
};

export const mapBooking = (item: any, index: number) => {
  console.log()
  return {
    bookingId:item['bookingId'],
    key: `${index}`,
    cubicleId: item['cubicle']['cubicleId'],
    employeeId: item['employee']['employeeId'],
    pk: item['bookingId'],
  };
};

export const mapDummyNotifications = () => {
  return [
    {
      key: 1,
      title: 'text',
      details: 'text',
      unread: true,
      objectIdentifier: 12345,
      pk: 123,
    },
  ];
};

export const mapBookingDetails = (response: any) => {
  var result: any = [];
  Object.keys(response).forEach(function(key) {
    if(key==="dateCreated"||key==="lastModified"){return;}
    if(key==="cubicle"){result.push({"name":key,"value":response["cubicle"]["cubicleId"]});return;}
    if(key==="employee"){result.push({"name":key,"value":response["employee"]["employeeId"]});return;}

    if(key==="startDate"||key==="endDate"){
      var t = new Date(response[key]);
      var formatted = ('0' + t.getDate()).slice(-2) 
          + '/' + ('0' + t.getMonth()).slice(-2)
          + '/' + (t.getFullYear());
      result.push({"name":key,"value":formatted})}

    else{result.push({"name":key,"value":response[key]})}
 
  })

  const attributes = result.map((attribute: any) => {
    return {
      label: attribute.name,
      value: attribute.value,
    };
  });
  return attributes;
};

export const mapCubicleDetails = (response: any) => {
  var result: any = [];
  Object.keys(response).forEach(function(key) {
    if(key==="lastModified"|| key==="dateCreated"){return;}
    if(key==="location"){var ob={"name":key,"value":response["location"]["locationName"]};}
    else{var ob={"name":key,"value":response[key]}}
    result.push(ob);
  })

  const attributes = result.map((attribute: any) => {
    return {
      label: attribute.name,
      value: attribute.value,
      
    };
  });
  return attributes;
};




export const mapSearchDetails = (response: any, meta: any, index: number) => {
  const attributes = response.data[index].map((attribute: any) => {
    return {
      label: attribute.name,
      value: attribute.type == 'MONEY' ? '$' + (attribute.value.money < 0 ? `(${-1 * attribute.value.money})` : attribute.value.money) : (attribute.type == 'ENUM' ? attribute.value.displayName : attribute.value),
    };
  });
  return meta.map((item: any) => {
    return {
      label: item.name,
      value: attributes.find(obj => obj.label === item.field).value,
    };
  })

};
