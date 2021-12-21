const baseURL = "/";

function handleError(resp) {
    if (400 <= resp.status && resp.status < 500) {
        return resp.json().then(data => {
            Vue.prototype.$Message.error({
                render:function (h){
                  return h("pre",data.message);
                },
                duration: 30,
                closable:true
            });
            return Promise.reject(data.message);
        });

    }
    if (resp.status >= 500) {
        return resp.json().then(data => {
            Vue.prototype.$Message.error({
                content: "Unknown Error",
                duration: 3,
            });
            return Promise.reject(data.message);
        });
    }
    return Promise.reject(resp.status);
};

function handleOk(resp) {
    return resp.text().then(txt => {
        if (!txt) {
            return Promise.resolve();
        }
        try {
            return Promise.resolve(JSON.parse(txt));
        } catch (e) {
            return Promise.reject(e);
        }
    });
}

Vue.prototype.fetch = window.fetchApi = {
    get(url, data) {
        if (!(data instanceof URLSearchParams)) {
            data = new URLSearchParams(data);
        }
        url = url + "?" + data.toString();
        return fetch(url, {
            method: "GET"
        }).then(function (resp) {
            if (resp.ok) {
                return handleOk(resp);
            }
            return handleError(resp);
        });
    },
    post(url, data) {
        return fetch(url, {
            method: "POST",
            headers: new Headers({
                'Content-Type': 'application/json;charset=utf-8',
            }),
            body: JSON.stringify(data)
        }).then(function (resp) {
            try {
                if (resp.ok) {
                    return handleOk(resp);
                }
                return handleError(resp);
            } catch (e) {

            }
        });
    },
    put(url, data) {
        return fetch(url, {
            method: "PUT",
            headers: new Headers({
                'Content-Type': 'application/json;charset=utf-8',
            }),
            body: JSON.stringify(data)
        }).then(function (resp) {
            if (resp.ok) {
                return handleOk(resp);
            }
            return handleError(resp);
        });
    },
    delete(url, data) {
        if (!(data instanceof URLSearchParams)) {
            data = new URLSearchParams(data);
        }
        url = url + "?" + data.toString();
        return fetch(url, {
            method: "DELETE",
        }).then(function (resp) {
            if (resp.ok) {
                return handleOk(resp);
            }
            return handleError(resp);
        });
    },
    patch(url, data) {
        return fetch(url, {
            method: "PUT",
            headers: new Headers({
                'Content-Type': 'application/json;charset=utf-8',
            }),
            body: JSON.stringify(data)
        }).then(function (resp) {
            if (resp.ok) {
                return handleOk(resp);
            }
            handleError(resp);
        });
    }

};


if (window.top === window) {
    window.top.windows = new Set();
    window.postsMessage = function (msg) {
        for (let w of window.top.windows) {
            w.postMessage(msg, "*");
        }
        window.postMessage(msg, "*");
    };

} else {
    //不是顶层窗口，注册
    if (window.top.windows.add(window)) {
        console.log("注册window成功,当前windows数量", window.top.windows.size);
    }
}
window.addEventListener("message", function (evt) {
    window.dispatchEvent(new Event(evt.data.type,{"bubbles":false, "cancelable":true}));
});


Object.assign(String.prototype, {
    firstCharUpperCase() {
        if (this.length === 0) {
            return this;
        }
        return this.charAt(0).toUpperCase() + this.substr(1);
    }
});
String.format = function () {
    let s = arguments[0];
    for (let i = 1; i < arguments.length; i++) {
        s = s.replace("{}", arguments[i]);
    }
    return s;
};





