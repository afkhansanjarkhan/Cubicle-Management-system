export const getEnumAsObjectArray = (enumm: string) => {
    return (JSON.parse(localStorage.getItem("bootstrap") || ''))
    [enumm].map((item: any) => { return { 'label': item, 'value': item }; });
};
