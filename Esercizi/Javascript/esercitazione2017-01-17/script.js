var Q_LIST_E_REQ_URI="http://globus.dmi.unict.it/~fmessina/esList.json";
var Q_DESC_E_REQ_URI="http://globus.dmi.unict.it/~fmessina/es.json";

var EXT_BASE_URI="http://globus.dmi.unict.it/~fmessina/";

var COMBO_DEF_LINE_TXT="--- CHOOSE ---";
var COMBO_EMP_LINE_TXT="--- EMPTY ---";

var lista_essenze=null;
var descrizioni_essenze=null;
var empty_opt = document.createElement("option");
empty_opt.value=COMBO_EMP_LINE_TXT;
empty_opt.innerHTML=COMBO_EMP_LINE_TXT;
var essenze_combobox=null;

var current_essenza_desc=null;

function do_when_ready() {
    essenze_combobox=document.getElementById("essenzeCombobox");
    essenze_combobox.appendChild(empty_opt);
}

document.addEventListener('DOMContentLoaded', do_when_ready, false);

function fill_essenze(lista) {
    essenze_combobox.innerHTML="";
    var def_line=document.createElement("option");
    def_line.value=COMBO_DEF_LINE_TXT
    def_line.innerHTML=COMBO_DEF_LINE_TXT;
    essenze_combobox.appendChild(def_line);
    for (var i in lista) {
        let n_line=document.createElement("option");
        n_line.value=lista[i];
        n_line.innerHTML=lista[i];
        essenze_combobox.appendChild(n_line);
    }
    def_line.selected=true;
}

function make_req(uri, callback, is_json) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", uri, true);
    xhr.onload = function (e) {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                if (is_json) {
                    callback(JSON.parse(xhr.responseText));
                }
                else {
                    callback(xhr.responseText);
                }
            }
            else {
              console.error(xhr.statusText);
            }
        }
    };
    xhr.onerror = function (e) {
      console.error(xhr.statusText);
    };
    xhr.send(null);
}

function queryListaEssenze() {
    if (!lista_essenze) {
        make_req(Q_LIST_E_REQ_URI, function(res) {
            lista_essenze = res;
            fill_essenze(lista_essenze);
        }, true);
    }
}

function show_desc() {
    var m_img=document.createElement('img');
    m_img.src=EXT_BASE_URI+current_essenza_desc['image-src'];
    var img_box=document.getElementById("imgBox");
    img_box.innerHTML="";
    img_box.appendChild(m_img);

    make_req(EXT_BASE_URI+current_essenza_desc['description'], function(res) {
        res_l=res.split('\n\n');
        var desc_box=document.getElementById("descBox");
        desc_box.innerHTML="";
        for (var i in res_l) {
            var m_par=document.createElement('p');
            m_par.innerHTML=res_l[i];
            desc_box.appendChild(m_par);
        }
    }, false);
}

function queryDescrizioneEssenza() {
    if (!descrizioni_essenze) {
        make_req(Q_DESC_E_REQ_URI, function(res) {
            descrizioni_essenze = res;
            queryDescrizioneEssenza();
        }, true);
    }
    else {
        var selected = essenze_combobox.selectedOptions[0].value;
        if (descrizioni_essenze[selected]) {
            current_essenza_desc=descrizioni_essenze[selected];
            show_desc();
        }
        else {
            alert("No entry for value "+selected);
        }
    }
}
