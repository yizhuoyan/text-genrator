(function(vm){

    var packageName=vm.packageName;
    var className=$.upperFirstChar(vm.name);
    var directionName=packageName.replace(/\./g,"/");

    return {
        "ClassName": className,
        "ClassNameEntity": className+"Entity",
        "directionName": directionName
    };
})({});