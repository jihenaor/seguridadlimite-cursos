package com.seguridadlimite.models.aprendiz.application.departamentos;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DepartamentoServiceImpl {

	public static String getDepartamento(String codigo) {
		if (Objects.isNull(codigo)) {
			return codigo;
		}
		switch (codigo) {
			case "63": return "QUINDIO";
			case "66": return "RISARALDA";
		    case "17": return "CALDAS";
		    case "5": return "ANTIOQUIA";
		    case "8": return "ATLANTICO";
		    case "11": return "SANTAFE DE BOGOTA";
		    case "13": return "BOLIVAR";
		    case "15": return "BOYACA";
		    case "18": return "CAQUETA";
		    case "19": return "CAUCA";
		    case "20": return "CESAR";
		    case "23": return "CORDOBA";
		    case "25": return "CUNDINAMARCA";
		    case "27": return "CHOCO";
		    case "41": return "HUILA";
		    case "44": return "LA GUAJIRA";
		    case "47": return "MAGDALENA";
		    case "50": return "META";
		    case "52": return "NARIÑO";
		    case "54": return "NORTE DE SANTANDER";
		    case "68": return "SANTANDER";
		    case "70": return "SUCRE";
		    case "73": return "TOLIMA";
		    case "76": return "VALLE DEL CAUCA";
		    case "81": return "ARAUCA";
		    case "85": return "CASANARE";
		    case "86": return "PUTUMAYO";
		    case "88": return "ARCHIPIELAGO DE SAN ANDRES";
		    case "91": return "AMAZONAS";
		    case "94": return "GUAINIA";
		    case "95": return "GUAVIARE";
		    case "97": return "VAUPES";
		    case "99": return "VICHADA";
		    default: return codigo;
		}
	}

	public static String getCiudad(String depto, String ciudad) {
		if (Objects.isNull(depto)) {
			return ciudad;
		}
	    switch (depto) {
	      case "5":
	    	  switch (ciudad) {
	    	  	case "1": return "MEDELLIN";
		        case "2": return "ABEJORRAL";
		        case "4": return "ABRIAQUI";
		        case "21": return "ALEJANDRIA";
		        case "30": return "AMAGA";
		        case "31": return "AMALFI";
		        case "34": return "ANDES";
		        case "36": return "ANGELOPOLIS";
		        case "38": return "ANGOSTURA";
		        case "40": return "ANORI";
		        case "42": return "SANTAFE DE ANTIOQUIA";
		        case "44": return "ANZA";
		        case "45": return "APARTADO";
		        case "51": return "ARBOLETES";
		        case "55": return "ARGELIA";
		        case "59": return "ARMENIA";
		        case "79": return "BARBOSA";
		        case "86": return "BELMIRA";
		        case "88": return "BELLO";
		        case "91": return "BETANIA";
		        case "93": return "BETULIA";
		        case "101": return "CIUDAD BOLIVAR";
		        case "107": return "BRICEÑO";
		        case "113": return "BURITICA";
		        case "120": return "CACERES";
		        case "125": return "CAICEDO";
		        case "129": return "CALDAS";
		        case "134": return "CAMPAMENTO";
		        case "138": return "CAÑAS GORDAS";
		        case "142": return "CARACOLI";
		        case "145": return "CARAMANTA";
		        case "147": return "CAREPA";
		        case "148": return "CARMEN DE VIBORAL";
		        case "150": return "CAROLINA";
		        case "154": return "CAUCASIA";
		        case "172": return "CHIGORODO";
		        case "190": return "CISNEROS";
		        case "197": return "COCORNA";
		        case "206": return "CONCEPCION";
		        case "209": return "CONCORDIA";
		        case "212": return "COPACABANA";
		        case "234": return "DABEIBA";
		        case "237": return "DON MATIAS";
		        case "240": return "EBEJICO";
		        case "250": return "EL BAGRE";
		        case "264": return "ENTRERRIOS";
		        case "266": return "ENVIGADO";
		        case "282": return "FREDONIA";
		        case "284": return "FRONTINO";
		        case "306": return "GIRALDO";
		        case "308": return "GIRARDOTA";
		        case "310": return "GOMEZ PLATA";
		        case "313": return "GRANADA";
		        case "315": return "GUADALUPE";
		        case "318": return "GUARNE";
		        case "321": return "GUATAPE";
		        case "347": return "HELICONIA";
		        case "353": return "HISPANIA";
		        case "360": return "ITAGUI";
		        case "361": return "ITUANGO";
		        case "364": return "JARDIN";
		        case "368": return "JERICO";
		        case "376": return "LA CEJA";
		        case "380": return "LA ESTRELLA";
		        case "390": return "La Pintada";
		        case "400": return "LA UNION";
		        case "411": return "LIBORINA";
		        case "425": return "MACEO";
		        case "440": return "MARINILLA";
		        case "467": return "MONTEBELLO";
		        case "475": return "MURINDO";
		        case "480": return "MUTATA";
		        case "483": return "NARIÑO";
		        case "490": return "NECOCLI";
		        case "495": return "NECHI";
		        case "501": return "OLAYA";
		        case "541": return "PEÑOL";
		        case "543": return "PEQUE";
		        case "576": return "PUEBLORRICO";
		        case "579": return "PUERTO BERRIO";
		        case "585": return "PUERTO NARE";
		        case "591": return "PUERTO TRIUNFO";
		        case "604": return "REMEDIOS";
		        case "607": return "RETIRO";
		        case "615": return "RIONEGRO";
		        case "628": return "SABANALARGA";
		        case "631": return "SABANETA";
		        case "642": return "SALGAR";
		        case "647": return "SAN ANDRES";
		        case "649": return "SAN CARLOS";
		        case "652": return "SAN FRANCISCO";
		        case "656": return "SAN JERONIMO";
		        case "658": return "SAN JOSE DE LA MONTAÑA";
		        case "659": return "SAN JUAN DE URABA";
		        case "660": return "SAN LUIS";
		        case "664": return "SAN PEDRO";
		        case "665": return "SAN PEDRO DE URABA";
		        case "667": return "SAN RAFAEL";
		        case "670": return "SAN ROQUE";
		        case "674": return "SAN VICENTE";
		        case "679": return "SANTA BARBARA";
		        case "686": return "SANTA ROSA DE OSOS";
		        case "690": return "SANTO DOMINGO";
		        case "697": return "SANTUARIO";
		        case "736": return "SEGOVIA";
		        case "756": return "SONSON";
		        case "761": return "SOPETRAN";
		        case "789": return "TAMESIS";
		        case "790": return "TARAZA";
		        case "792": return "TARSO";
		        case "809": return "TITIRIBI";
		        case "819": return "TOLEDO";
		        case "837": return "TURBO";
		        case "842": return "URAMITA";
		        case "847": return "URRAO";
		        case "854": return "VALDIVIA";
		        case "856": return "VALPARAISO";
		        case "858": return "VEGACHI";
		        case "861": return "VENECIA";
		        case "873": return "VIGIA DEL FUERTE";
		        case "885": return "YALI";
		        case "887": return "YARUMAL";
		        case "890": return "YOLOMBO";
		        case "893": return "YONDO";
		        case "895": return "ZARAGOZA";
		        default: return ciudad;
	    	  }
	      case "8":
	    	  switch (ciudad) {
		        case "1": return "BARRANQUILLA";
		        case "78": return "BARANOA";
		        case "137": return "CAMPO DE LA CRUZ";
		        case "141": return "CANDELARIA";
		        case "296": return "GALAPA";
		        case "372": return "JUAN DE ACOSTA";
		        case "421": return "LURUACO";
		        case "433": return "MALAMBO";
		        case "436": return "MANATI";
		        case "520": return "PALMAR DE VARELA";
		        case "549": return "PIOJO";
		        case "558": return "POLONUEVO";
		        case "560": return "PONEDERA";
		        case "573": return "PUERTO COLOMBIA";
		        case "606": return "REPELON";
		        case "634": return "SABANAGRANDE";
		        case "638": return "SABANALARGA";
		        case "675": return "SANTA LUCIA";
		        case "685": return "SANTO TOMAS";
		        case "758": return "SOLEDAD";
		        case "770": return "SUAN";
		        case "832": return "TUBARA";
		        case "849": return "USIACURI";
		        default: return ciudad;
	    	  }
	      case "11":
	    	  switch (ciudad) {
	    	  	case "1": return "BOGOTA D.C.";
	    	  	default: return ciudad;
	    	  }
	      case "13":
	    	   switch (ciudad) {
		        case "1": return "CARTAGENA";
		        case "6": return "ACHI";
		        case "30": return "ALTOS DEL ROSARIO";
		        case "42": return "ARENAL";
		        case "52": return "ARJONA";
		        case "62": return "Arroyohondo";
		        case "74": return "BARRANCO DE LOBA";
		        case "140": return "CALAMAR";
		        case "160": return "CANTAGALLO";
		        case "188": return "CICUCO";
		        case "212": return "CORDOBA";
		        case "222": return "CLEMENCIA";
		        case "244": return "EL CARMEN DE BOLIVAR";
		        case "248": return "EL GUAMO";
		        case "268": return "EL PEÑON";
		        case "300": return "HATILLO DE LOBA";
		        case "430": return "MAGANGUE";
		        case "433": return "MAHATES";
		        case "440": return "MARGARITA";
		        case "442": return "MARIA LA BAJA";
		        case "458": return "MONTECRISTO";
		        case "468": return "MOMPOS";
		        case "473": return "MORALES";
		        case "490": return "Norosi";
		        case "549": return "PINILLOS";
		        case "580": return "REGIDOR";
		        case "600": return "RIO VIEJO";
		        case "620": return "SAN CRISTOBAL";
		        case "647": return "SAN ESTANISLAO";
		        case "650": return "SAN FERNANDO";
		        case "654": return "SAN JACINTO";
		        case "655": return "San Jacinto del Cauca";
		        case "657": return "SAN JUAN NEPOMUCENO";
		        case "667": return "SAN MARTIN DE LOBA";
		        case "670": return "SAN PABLO";
		        case "673": return "SANTA CATALINA";
		        case "683": return "SANTA ROSA";
		        case "688": return "SANTA ROSA DEL SUR";
		        case "744": return "SIMITI";
		        case "760": return "SOPLAVIENTO";
		        case "780": return "TALAIGUA NUEVO";
		        case "810": return "TIQUISIO";
		        case "836": return "TURBACO";
		        case "838": return "TURBANA";
		        case "873": return "VILLANUEVA";
		        case "894": return "ZAMBRANO";
		        default: return ciudad;
	    	  }
	      case "1":
	    	  switch (ciudad) {
		        case "1": return "TUNJA";
		        case "22": return "ALMEIDA";
		        case "47": return "AQUITANIA";
		        case "51": return "ARCABUCO";
		        case "87": return "BELEN";
		        case "90": return "BERBEO";
		        case "92": return "BETEITIVA";
		        case "97": return "BOAVITA";
		        case "104": return "BOYACA";
		        case "106": return "BRICEÑO";
		        case "109": return "BUENAVISTA";
		        case "114": return "BUSBANZA";
		        case "131": return "CALDAS";
		        case "135": return "CAMPOHERMOSO";
		        case "162": return "CERINZA";
		        case "172": return "CHINAVITA";
		        case "176": return "CHIQUINQUIRA";
		        case "180": return "CHISCAS";
		        case "183": return "CHITA";
		        case "185": return "CHITARAQUE";
		        case "187": return "CHIVATA";
		        case "189": return "CIENEGA";
		        case "204": return "COMBITA";
		        case "212": return "COPER";
		        case "215": return "CORRALES";
		        case "218": return "COVARACHIA";
		        case "223": return "CUBARA";
		        case "224": return "CUCAITA";
		        case "226": return "CUITIVA";
		        case "232": return "CHIQUIZA";
		        case "236": return "CHIVOR";
		        case "238": return "DUITAMA";
		        case "244": return "EL COCUY";
		        case "248": return "EL ESPINO";
		        case "272": return "FIRAVITOBA";
		        case "276": return "FLORESTA";
		        case "293": return "GACHANTIVA";
		        case "296": return "GAMEZA";
		        case "299": return "GARAGOA";
		        case "317": return "GUACAMAYAS";
		        case "322": return "GUATEQUE";
		        case "325": return "GUAYATA";
		        case "332": return "GUICAN";
		        case "362": return "IZA";
		        case "367": return "JENESANO";
		        case "368": return "JERICO";
		        case "377": return "LABRANZAGRANDE";
		        case "380": return "LA CAPILLA";
		        case "401": return "LA VICTORIA";
		        case "403": return "LA UVITA";
		        case "407": return "VILLA DE LEYVA";
		        case "425": return "MACANAL";
		        case "442": return "MARIPI";
		        case "455": return "MIRAFLORES";
		        case "464": return "MONGUA";
		        case "466": return "MONGUI";
		        case "469": return "MONIQUIRA";
		        case "476": return "MOTAVITA";
		        case "480": return "MUZO";
		        case "491": return "NOBSA";
		        case "494": return "NUEVO COLON";
		        case "500": return "OICATA";
		        case "507": return "OTANCHE";
		        case "511": return "PACHAVITA";
		        case "514": return "PAEZ";
		        case "516": return "PAIPA";
		        case "518": return "PAJARITO";
		        case "522": return "PANQUEBA";
		        case "531": return "PAUNA";
		        case "533": return "PAYA";
		        case "537": return "PAZ DE RIO";
		        case "542": return "PESCA";
		        case "550": return "PISBA";
		        case "572": return "PUERTO BOYACA";
		        case "580": return "QUIPAMA";
		        case "599": return "RAMIRIQUI";
		        case "600": return "RAQUIRA";
		        case "621": return "RONDON";
		        case "632": return "SABOYA";
		        case "638": return "SACHICA";
		        case "646": return "SAMACA";
		        case "660": return "SAN EDUARDO";
		        case "664": return "SAN JOSE DE PARE";
		        case "667": return "SAN LUIS DE GACENO";
		        case "673": return "SAN MATEO";
		        case "676": return "SAN MIGUEL DE SEMA";
		        case "681": return "SAN PABLO BORBUR";
		        case "686": return "SANTANA";
		        case "690": return "SANTA MARIA";
		        case "693": return "SAN ROSA VITERBO";
		        case "696": return "SANTA SOFIA";
		        case "720": return "SATIVANORTE";
		        case "723": return "SATIVASUR";
		        case "740": return "SIACHOQUE";
		        case "753": return "SOATA";
		        case "755": return "SOCOTA";
		        case "757": return "SOCHA";
		        case "759": return "SOGAMOSO";
		        case "761": return "SOMONDOCO";
		        case "762": return "SORA";
		        case "763": return "SOTAQUIRA";
		        case "764": return "SORACA";
		        case "774": return "SUSACON";
		        case "776": return "SUTAMARCHAN";
		        case "778": return "SUTATENZA";
		        case "790": return "TASCO";
		        case "798": return "TENZA";
		        case "804": return "TIBANA";
		        case "806": return "TIBASOSA";
		        case "808": return "TINJACA";
		        case "810": return "TIPACOQUE";
		        case "814": return "TOCA";
		        case "816": return "TOGUI";
		        case "820": return "TOPAGA";
		        case "822": return "TOTA";
		        case "832": return "TUNUNGUA";
		        case "835": return "TURMEQUE";
		        case "837": return "TUTA";
		        case "839": return "TUTAZA";
		        case "842": return "UMBITA";
		        case "861": return "VENTAQUEMADA";
		        case "879": return "VIRACACHA";
		        case "897": return "ZETAQUIRA";
		        default: return ciudad;
	    	  }
	      case "17":
	    	  switch (ciudad) {
		        case "1": return "MANIZALES";
		        case "13": return "AGUADAS";
		        case "42": return "ANSERMA";
		        case "50": return "ARANZAZU";
		        case "88": return "BELALCAZAR";
		        case "174": return "CHINCHINA";
		        case "272": return "FILADELFIA";
		        case "380": return "LA DORADA";
		        case "388": return "LA MERCED";
		        case "433": return "MANZANARES";
		        case "442": return "MARMATO";
		        case "444": return "MARQUETALIA";
		        case "446": return "MARULANDA";
		        case "486": return "NEIRA";
		        case "495": return "Norcasia";
		        case "513": return "PACORA";
		        case "524": return "PALESTINA";
		        case "541": return "PENSILVANIA";
		        case "614": return "RIOSUCIO";
		        case "616": return "RISARALDA";
		        case "653": return "SALAMINA";
		        case "662": return "SAMANA";
		        case "665": return "SAN JOSE";
		        case "777": return "SUPIA";
		        case "867": return "VICTORIA";
		        case "873": return "VILLAMARIA";
		        case "877": return "VITERBO";
		        default: return ciudad;
	    	  }
	      case "18":
	    	  switch (ciudad) {
		        case "1": return "FLORENCIA";
		        case "29": return "ALBANIA";
		        case "94": return "BELEN DE LOS ANDAQUIES";
		        case "150": return "CARTAGENA DEL CHAIRA";
		        case "205": return "CURRILLO";
		        case "247": return "EL DONCELLO";
		        case "256": return "EL PAUJIL";
		        case "410": return "LA MONTAÑITA";
		        case "460": return "MILAN";
		        case "479": return "MORELIA";
		        case "592": return "PUERTO RICO";
		        case "610": return "SAN JOSE DE LA FRAGUA";
		        case "753": return "SAN VICENTE DEL CAGUAN";
		        case "756": return "SOLANO";
		        case "785": return "SOLITA";
		        case "860": return "VALPARAISO";
		        default: return ciudad;
	    	  }

	      case "19":
	    	  switch (ciudad) {
		        case "1": return "POPAYAN";
		        case "22": return "ALMAGUER";
		        case "50": return "ARGELIA";
		        case "75": return "BALBOA";
		        case "100": return "BOLIVAR";
		        case "110": return "BUENOS AIRES";
		        case "130": return "CAJIBIO";
		        case "137": return "CALDONO";
		        case "142": return "CALOTO";
		        case "212": return "CORINTO";
		        case "256": return "EL TAMBO";
		        case "290": return "FLORENCIA";
		        case "300": return "Guachené";
		        case "318": return "GUAPI";
		        case "355": return "INZA";
		        case "364": return "JAMBALO";
		        case "392": return "LA SIERRA";
		        case "397": return "LA VEGA";
		        case "418": return "LOPEZ";
		        case "450": return "MERCADERES";
		        case "455": return "MIRANDA";
		        case "473": return "MORALES";
		        case "513": return "PADILLA";
		        case "517": return "PAEZ";
		        case "532": return "PATIA";
		        case "533": return "PIAMONTE";
		        case "548": return "PIENDAMO";
		        case "573": return "PUERTO TEJADA";
		        case "585": return "PURACE";
		        case "622": return "ROSAS";
		        case "693": return "SAN SEBASTIAN";
		        case "698": return "SANTANDER DE QUILICHAO";
		        case "701": return "SANTA ROSA";
		        case "743": return "SILVIA";
		        case "760": return "SOTARA";
		        case "780": return "SUAREZ";
		        case "785": return "Sucre";
		        case "807": return "TIMBIO";
		        case "809": return "TIMBIQUI";
		        case "821": return "TORIBIO";
		        case "824": return "TOTORO";
		        case "845": return "Villa Rica";
		        default: return ciudad;
	    	  }

	      case "20":
	    	  switch (ciudad) {
		        case "1": return "VALLEDUPAR";
		        case "11": return "AGUACHICA";
		        case "13": return "AGUSTIN CODAZZI";
		        case "32": return "ASTREA";
		        case "45": return "Becerril";
		        case "60": return "BOSCONIA";
		        case "175": return "CHIMICHAGUA";
		        case "178": return "CHIRIGUANA";
		        case "228": return "CURUMANI";
		        case "238": return "EL COPEY";
		        case "250": return "EL PASO";
		        case "295": return "GAMARRA";
		        case "310": return "GONZALEZ";
		        case "383": return "LA GLORIA";
		        case "400": return "LA JAGUA DE IBIRICO";
		        case "443": return "MANAURE";
		        case "517": return "PAILITAS";
		        case "550": return "PELAYA";
		        case "570": return "Pueblo Bello";
		        case "614": return "RIO DE ORO";
		        case "621": return "LA PAZ";
		        case "710": return "SAN ALBERTO";
		        case "750": return "SAN DIEGO";
		        case "770": return "SAN MARTIN";
		        case "787": return "TAMALAMEQUE";
		        default: return ciudad;
	    	  }

	      case "23":
	    	  switch (ciudad) {
		        case "1": return "MONTERIA";
		        case "68": return "AYAPEL";
		        case "79": return "BUENAVISTA";
		        case "90": return "CANALETE";
		        case "162": return "CERETE";
		        case "168": return "CHIMA";
		        case "182": return "CHINU";
		        case "189": return "CIENAGA DE ORO";
		        case "300": return "Cotorra";
		        case "350": return "La Apartada";
		        case "417": return "LORICA";
		        case "419": return "LOS CORDOBAS";
		        case "464": return "MOMIL";
		        case "466": return "MONTELIBANO";
		        case "500": return "MOÑITOS";
		        case "555": return "PLANETA RICA";
		        case "570": return "PUEBLO NUEVO";
		        case "574": return "PUERTO ESCONDIDO";
		        case "580": return "PUERTO LIBERTADOR";
		        case "586": return "PURISIMA";
		        case "660": return "SAHAGUN";
		        case "670": return "SAN ANDRES DE SOTAVENTO";
		        case "672": return "SAN ANTERO";
		        case "675": return "SAN BERNARDO DEL VIENTO";
		        case "678": return "SAN CARLOS";
		        case "682": return "San José De Uré";
		        case "686": return "SAN PELAYO";
		        case "807": return "TIERRALTA";
		        case "815": return "Tuchín";
		        case "855": return "Valencia";
		        default: return ciudad;
	    	  }

	      case "2":
	    	  switch (ciudad) {
		        case "1": return "AGUA DE DIOS -BOGOTA-";
		        case "19": return "ALBAN";
		        case "35": return "ANAPOIMA";
		        case "40": return "ANOLAIMA";
		        case "53": return "ARBELAEZ";
		        case "86": return "BELTRAN";
		        case "95": return "BITUIMA";
		        case "99": return "BOJACA";
		        case "120": return "CABRERA";
		        case "123": return "CACHIPAY";
		        case "126": return "CAJICA";
		        case "148": return "CAPARRAPI";
		        case "151": return "CAQUEZA";
		        case "154": return "CARMEN DE CARUPA";
		        case "168": return "CHAGUANI";
		        case "175": return "CHIA";
		        case "178": return "CHIPAQUE";
		        case "181": return "CHOACHI";
		        case "183": return "CHOCONTA";
		        case "200": return "COGUA";
		        case "214": return "COTA";
		        case "224": return "CUCUNUBA";
		        case "245": return "EL COLEGIO";
		        case "258": return "El Peñón";
		        case "260": return "El Rosal";
		        case "269": return "FACATATIVA";
		        case "279": return "FOMEQUE";
		        case "281": return "FOSCA";
		        case "286": return "FUNZA";
		        case "288": return "FUQUENE";
		        case "290": return "FUSAGASUGA";
		        case "293": return "GACHALA";
		        case "295": return "GACHANCIPA";
		        case "297": return "GACHETA";
		        case "299": return "GAMA";
		        case "307": return "GIRARDOT";
		        case "312": return "GRANADA";
		        case "317": return "GUACHETA";
		        case "320": return "GUADUAS";
		        case "322": return "GUASCA";
		        case "324": return "GUATAQUI";
		        case "326": return "GUATAVITA";
		        case "328": return "GUAYABAL DE SIQUIMA";
		        case "335": return "GUAYABETAL";
		        case "339": return "GUTIERREZ";
		        case "368": return "JERUSALEM";
		        case "372": return "JUNIN";
		        case "377": return "LA CALERA";
		        case "386": return "LA MESA";
		        case "394": return "LA PALMA";
		        case "398": return "LA PEÑA";
		        case "402": return "LA VEGA";
		        case "407": return "LENGUAZAQUE";
		        case "426": return "MACHETA";
		        case "430": return "MADRID";
		        case "436": return "MANTA";
		        case "438": return "MEDINA";
		        case "473": return "MOSQUERA";
		        case "483": return "NARIÑO";
		        case "486": return "NEMOCON";
		        case "488": return "NILO";
		        case "489": return "NIMAIMA";
		        case "491": return "NOCAIMA";
		        case "506": return "OSPINA PEREZ";
		        case "513": return "PACHO";
		        case "518": return "PAIME";
		        case "524": return "PANDI";
		        case "530": return "PARATEBUENO";
		        case "535": return "PASCA";
		        case "572": return "PUERTO SALGAR";
		        case "580": return "PULI";
		        case "592": return "QUEBRADANEGRA";
		        case "594": return "QUETAME";
		        case "596": return "QUIPILE";
		        case "599": return "RAFAEL REYES";
		        case "612": return "RICAURTE";
		        case "645": return "SAN ANTONIO DE TENA";
		        case "649": return "SAN BERNARDO";
		        case "653": return "SAN CAYETANO";
		        case "658": return "SAN FRANCISCO";
		        case "662": return "SAN JUAN DE RIOSECO";
		        case "718": return "SASAIMA";
		        case "736": return "SESQUILE";
		        case "740": return "SIBATE";
		        case "743": return "SILVANIA";
		        case "745": return "SIMIJACA";
		        case "754": return "SOACHA";
		        case "758": return "SOPO";
		        case "769": return "SUBACHOQUE";
		        case "772": return "SUESCA";
		        case "777": return "SUPATA";
		        case "779": return "SUSA";
		        case "781": return "SUTATAUSA";
		        case "785": return "TABIO";
		        case "793": return "TAUSA";
		        case "797": return "TENA";
		        case "799": return "TENJO";
		        case "805": return "TIBACUY";
		        case "807": return "TIBIRITA";
		        case "815": return "TOCAIMA";
		        case "817": return "TOCANCIPA";
		        case "823": return "TOPAIPI";
		        case "839": return "UBALA";
		        case "841": return "UBAQUE";
		        case "843": return "UBATE";
		        case "845": return "UNE";
		        case "851": return "UTICA";
		        case "862": return "VERGARA";
		        case "867": return "VIANI";
		        case "871": return "VILLAGOMEZ";
		        case "873": return "VILLAPINZON";
		        case "875": return "VILLETA";
		        case "878": return "VIOTA";
		        case "885": return "YACOPI";
		        case "898": return "ZIPACON";
		        case "899": return "ZIPAQUIRA";
		        default: return ciudad;
	    	  }

	      case "27":
	    	  switch (ciudad) {
		        case "1": return "QUIBDO";
		        case "6": return "ACANDI";
		        case "25": return "ALTO BAUDO";
		        case "50": return "Atrato";
		        case "73": return "BAGADO";
		        case "75": return "BAHIA SOLANO";
		        case "77": return "BAJO BAUDO";
		        case "86": return "Belén de Bajirá1";
		        case "99": return "BOJAYA";
		        case "135": return "EL CANTON DE SAN PABLO";
		        case "150": return "Carmen del Darien";
		        case "160": return "Cértegui";
		        case "205": return "CONDOTO";
		        case "245": return "EL CARMEN DE ATRATO";
		        case "250": return "LITORAL DEL BAJO SAN JUAN";
		        case "361": return "ITSMINA";
		        case "372": return "JURADO";
		        case "413": return "LLORO";
		        case "425": return "Medio Atrato";
		        case "430": return "Medio Baudó";
		        case "450": return "Medio San Juan";
		        case "491": return "NOVITA";
		        case "495": return "NUQUI";
		        case "580": return "Río Iro";
		        case "600": return "Río Quito2";
		        case "615": return "RIOSUCIO";
		        case "660": return "SAN JOSE DEL PALMAR";
		        case "745": return "SIPI";
		        case "787": return "TADO";
		        case "800": return "UNGUIA";
		        case "810": return "Unión Panamericana";
		        default: return ciudad;
	    	  }

	      case "41":
	    	  switch (ciudad) {
		        case "1": return "NEIVA";
		        case "6": return "ACEVEDO";
		        case "13": return "AGRADO";
		        case "16": return "AIPE";
		        case "20": return "ALGECIRAS";
		        case "26": return "ALTAMIRA";
		        case "78": return "BARAYA";
		        case "132": return "CAMPOALEGRE";
		        case "206": return "COLOMBIA";
		        case "244": return "ELIAS";
		        case "298": return "GARZON";
		        case "306": return "GIGANTE";
		        case "319": return "GUADALUPE";
		        case "349": return "HOBO";
		        case "357": return "IQUIRA";
		        case "359": return "ISNOS";
		        case "378": return "LA ARGENTINA";
		        case "396": return "LA PLATA";
		        case "483": return "NATAGA";
		        case "503": return "OPORAPA";
		        case "518": return "PAICOL";
		        case "524": return "PALERMO";
		        case "530": return "PALESTINA";
		        case "548": return "PITAL";
		        case "551": return "PITALITO";
		        case "615": return "RIVERA";
		        case "660": return "SALADOBLANCO";
		        case "668": return "SAN AGUSTIN";
		        case "676": return "SANTA MARIA";
		        case "770": return "SUAZA";
		        case "791": return "TARQUI";
		        case "797": return "TESALIA";
		        case "799": return "TELLO";
		        case "801": return "TERUEL";
		        case "807": return "TIMANA";
		        case "872": return "VILLAVIEJA";
		        case "885": return "YAGUARA";
		        default: return ciudad;
	    	  }

	      case "44":
	    	  switch (ciudad) {
		        case "1": return "RIOHACHA";
		        case "35": return "Albania";
		        case "78": return "BARRANCAS";
		        case "90": return "DIBULLA";
		        case "98": return "DISTRACCION";
		        case "110": return "EL MOLINO";
		        case "279": return "FONSECA";
		        case "378": return "HATONUEVO";
		        case "420": return "La Jagua del Pilar";
		        case "430": return "MAICAO";
		        case "560": return "MANAURE";
		        case "650": return "SAN JUAN DEL CESAR";
		        case "847": return "URIBIA";
		        case "855": return "URUMITA";
		        case "874": return "VILLANUEVA";
		        default: return ciudad;
	    	  }

	      case "47":
	    	  switch (ciudad) {
		        case "1": return "SANTA MARTA";
		        case "30": return "Algarrobo";
		        case "53": return "Aracataca";
		        case "58": return "ARIGUANI";
		        case "161": return "CERRO SAN ANTONIO";
		        case "170": return "CHIVOLO";
		        case "189": return "CIENAGA";
		        case "205": return "Concordia";
		        case "245": return "EL BANCO";
		        case "258": return "EL PIÑON";
		        case "268": return "EL RETEN";
		        case "288": return "FUNDACION";
		        case "318": return "GUAMAL";
		        case "460": return "Nueva Granada";
		        case "541": return "PEDRAZA";
		        case "545": return "PIJIÑO DEL CARMEN";
		        case "551": return "PIVIJAY";
		        case "555": return "PLATO";
		        case "570": return "PUEBLO VIEJO";
		        case "605": return "REMOLINO";
		        case "660": return "Sabanas de San Angel";
		        case "675": return "SALAMINA";
		        case "692": return "SAN SEBASTIAN BUENAVISTA";
		        case "703": return "SAN ZENON";
		        case "707": return "SANTA ANA";
		        case "720": return "Santa Bárbara de Pinto";
		        case "745": return "SITIONUEVO";
		        case "798": return "TENERIFE";
		        case "960": return "Zapayán";
		        case "980": return "Zona Bananera";
		        default: return ciudad;
	    	  }

	      case "50":
	    	  switch (ciudad) {
		        case "1": return "VILLAVICENCIO";
		        case "6": return "ACACIAS";
		        case "110": return "BARRANCA DE UPIA";
		        case "124": return "CABUYARO";
		        case "150": return "Castilla la Nueva";
		        case "223": return "Cubarral";
		        case "226": return "CUMARAL";
		        case "245": return "EL CALVARIO";
		        case "251": return "EL CASTILLO";
		        case "270": return "EL DORADO";
		        case "287": return "FUENTE DE ORO";
		        case "313": return "GRANADA";
		        case "318": return "GUAMAL";
		        case "325": return "MAPIRIPAN";
		        case "330": return "MESETAS";
		        case "350": return "La Macarena";
		        case "370": return "LA URIBE";
		        case "400": return "LEJANIAS";
		        case "450": return "Puerto Concordia";
		        case "568": return "PUERTO GAITAN";
		        case "573": return "PUERTO LOPEZ";
		        case "577": return "PUERTO LLERAS";
		        case "590": return "PUERTO RICO";
		        case "606": return "RESTREPO";
		        case "680": return "SAN CARLOS GUAROA";
		        case "683": return "SAN JUAN DE ARAMA";
		        case "686": return "SAN JUANITO";
		        case "689": return "SAN MARTIN";
		        case "711": return "VISTA HERMOSA";
		        default: return ciudad;
	    	  }

	      case "52":
	    	  switch (ciudad) {
		        case "1": return "PASTO";
		        case "19": return "ALBAN";
		        case "22": return "ALDANA";
		        case "36": return "ANCUYA";
		        case "51": return "ARBOLEDA";
		        case "79": return "BARBACOAS";
		        case "83": return "BELEN";
		        case "110": return "BUESACO";
		        case "203": return "COLON";
		        case "207": return "CONSACA";
		        case "210": return "CONTADERO";
		        case "215": return "CORDOBA";
		        case "224": return "CUASPUD";
		        case "227": return "CUMBAL";
		        case "233": return "CUMBITARA";
		        case "240": return "CHACHAGUI";
		        case "250": return "EL CHARCO";
		        case "254": return "El Peñol";
		        case "256": return "EL ROSARIO";
		        case "258": return "EL TABLON";
		        case "260": return "EL TAMBO";
		        case "287": return "FUNES";
		        case "317": return "GUACHUCAL";
		        case "320": return "GUAITARILLA";
		        case "323": return "GUALMATAN";
		        case "352": return "Iles";
		        case "354": return "IMUES";
		        case "356": return "IPIALES";
		        case "378": return "LA CRUZ";
		        case "381": return "LA FLORIDA";
		        case "385": return "LA LLANADA";
		        case "390": return "LA TOLA";
		        case "399": return "LA UNION";
		        case "405": return "LEIVA";
		        case "411": return "LINARES";
		        case "418": return "LOS ANDES";
		        case "427": return "MAGUI";
		        case "435": return "MALLAMA";
		        case "473": return "MOSQUERA";
		        case "480": return "Nariño";
		        case "490": return "OLAYA HERRERA";
		        case "506": return "OSPINA";
		        case "520": return "FRANCISCO PIZARRO";
		        case "540": return "POLICARPA";
		        case "560": return "POTOSI";
		        case "565": return "PROVIDENCIA";
		        case "573": return "PUERRES";
		        case "585": return "PUPIALES";
		        case "612": return "RICAURTE";
		        case "621": return "ROBERTO PAYAN";
		        case "678": return "SAMANIEGO";
		        case "683": return "SANDONA";
		        case "685": return "SAN BERNARDO";
		        case "687": return "SAN LORENZO";
		        case "693": return "SAN PABLO";
		        case "694": return "SAN PEDRO DE CARTAGO";
		        case "696": return "SANTA BARBARA";
		        case "699": return "SANTACRUZ";
		        case "720": return "SAPUYES";
		        case "786": return "TAMINANGO";
		        case "788": return "TANGUA";
		        case "835": return "TUMACO";
		        case "838": return "TUQUERRES";
		        case "885": return "YUCUANQUER";
		        default: return ciudad;
	    	  }

	      case "54":
	    	  switch (ciudad) {
		        case "1": return "CUCUTA";
		        case "3": return "ABREGO";
		        case "51": return "ARBOLEDAS";
		        case "99": return "BOCHALEMA";
		        case "109": return "BUCARASICA";
		        case "125": return "CACOTA";
		        case "128": return "CACHIRA";
		        case "172": return "CHINACOTA";
		        case "174": return "CHITAGA";
		        case "206": return "CONVENCION";
		        case "223": return "CUCUTILLA";
		        case "239": return "DURANIA";
		        case "245": return "EL CARMEN";
		        case "250": return "EL TARRA";
		        case "261": return "EL ZULIA";
		        case "313": return "GRAMALOTE";
		        case "344": return "HACARI";
		        case "347": return "HERRAN";
		        case "377": return "LABATECA";
		        case "385": return "LA ESPERANZA";
		        case "398": return "LA PLAYA";
		        case "405": return "LOS PATIOS";
		        case "418": return "LOURDES";
		        case "480": return "MUTISCUA";
		        case "498": return "OCAÑA";
		        case "518": return "PAMPLONA";
		        case "520": return "PAMPLONITA";
		        case "553": return "PUERTO SANTANDER";
		        case "599": return "RAGONVALIA";
		        case "660": return "SALAZAR";
		        case "670": return "SAN CALIXTO";
		        case "673": return "SAN CAYETANO";
		        case "680": return "SANTIAGO";
		        case "720": return "SARDINATA";
		        case "743": return "SILOS";
		        case "800": return "TEORAMA";
		        case "810": return "TIBU";
		        case "820": return "TOLEDO";
		        case "871": return "VILLA CARO";
		        case "874": return "VILLA DEL ROSARIO";
		        default: return ciudad;
	    	  }

	      case "63":
	    	  switch (ciudad) {
		        case "1": return "ARMENIA";
		        case "111": return "BUENAVISTA";
		        case "130": return "CALARCA";
		        case "190": return "CIRCASIA";
		        case "212": return "CORDOBA";
		        case "272": return "FILANDIA";
		        case "302": return "GENOVA";
		        case "401": return "LA TEBAIDA";
		        case "470": return "MONTENEGRO";
		        case "548": return "PIJAO";
		        case "594": return "QUIMBAYA";
		        case "690": return "SALENTO";
		        default: return ciudad;
	    	  }
	      case "66":
	    	  switch (ciudad) {
		        case "1": return "PEREIRA";
		        case "45": return "APIA";
		        case "75": return "BALBOA";
		        case "88": return "BELEN DE UMBRIA";
		        case "170": return "DOSQUEBRADAS";
		        case "318": return "GUATICA";
		        case "383": return "LA CELIA";
		        case "400": return "LA VIRGINIA";
		        case "440": return "MARSELLA";
		        case "456": return "MISTRATO";
		        case "572": return "PUEBLO RICO";
		        case "594": return "QUINCHIA";
		        case "682": return "SANTA ROSA DE CABAL";
		        case "687": return "SANTUARIO";
		        default: return ciudad;
	    	  }

	      case "68":
	    	  switch (ciudad) {
		        case "1": return "BUCARAMANGA";
		        case "13": return "AGUADA";
		        case "20": return "ALBANIA";
		        case "51": return "ARATOCA";
		        case "77": return "BARBOSA";
		        case "79": return "BARICHARA";
		        case "81": return "BARRANCABERMEJA";
		        case "92": return "BETULIA";
		        case "101": return "BOLIVAR";
		        case "121": return "CABRERA";
		        case "132": return "CALIFORNIA";
		        case "147": return "CAPITANEJO";
		        case "152": return "CARCASI";
		        case "160": return "CEPITA";
		        case "162": return "CERRITO";
		        case "167": return "CHARALA";
		        case "169": return "CHARTA";
		        case "176": return "CHIMA";
		        case "179": return "CHIPATA";
		        case "190": return "CIMITARRA";
		        case "207": return "CONCEPCION";
		        case "209": return "CONFINES";
		        case "211": return "CONTRATACION";
		        case "217": return "COROMORO";
		        case "229": return "CURITI";
		        case "235": return "EL CARMEN DE CHUCURI";
		        case "245": return "EL GUACAMAYO";
		        case "250": return "EL PEÑON";
		        case "255": return "EL PLAYON";
		        case "264": return "ENCINO";
		        case "266": return "ENCISO";
		        case "271": return "EL FLORIAN";
		        case "276": return "FLORIDABLANCA";
		        case "296": return "GALAN";
		        case "298": return "GAMBITA";
		        case "307": return "GIRON";
		        case "318": return "GUACA";
		        case "320": return "GUADALUPE";
		        case "322": return "GUAPOTA";
		        case "324": return "GUAVATA";
		        case "327": return "GUEPSA";
		        case "344": return "HATO";
		        case "368": return "Jesús María";
		        case "370": return "JORDAN";
		        case "377": return "LA BELLEZA";
		        case "385": return "LANDAZURI";
		        case "397": return "LA PAZ";
		        case "406": return "LEBRIJA";
		        case "418": return "LOS SANTOS";
		        case "425": return "MACARAVITA";
		        case "432": return "MALAGA";
		        case "444": return "MATANZA";
		        case "464": return "MOGOTES";
		        case "468": return "Molagavita";
		        case "498": return "OCAMONTE";
		        case "500": return "OIBA";
		        case "502": return "ONZAGA";
		        case "522": return "PALMAR";
		        case "524": return "PALMAS DEL SOCORRO";
		        case "533": return "PARAMO";
		        case "547": return "PIEDECUESTA";
		        case "549": return "PINCHOTE";
		        case "572": return "Puente Nacional";
		        case "573": return "Puerto Parra";
		        case "575": return "Puerto Wilches";
		        case "615": return "Rionegro";
		        case "655": return "Sabana de Torres";
		        case "669": return "San Andrés";
		        case "673": return "San Benito";
		        case "679": return "San Gil";
		        case "682": return "San Joaquín";
		        case "684": return "San José de Miranda";
		        case "686": return "San Miguel";
		        case "689": return "San Vicente de Chucurí";
		        case "705": return "Santa Bárbara";
		        case "720": return "Santa Helena del Opón";
		        case "745": return "Simacota";
		        case "755": return "Socorro";
		        case "770": return "Suaita";
		        case "773": return "Sucre";
		        case "780": return "Suratá";
		        case "820": return "Tona";
		        case "855": return "Valle de San José";
		        case "861": return "VELEZ SANTANDER";
		        case "867": return "Vetas";
		        case "872": return "Villanueva";
		        case "895": return "Zapatoca";
		        default: return ciudad;
	    	  }

	      case "70":
	    	  switch (ciudad) {
		        case "1": return "SINCELEJO";
		        case "110": return "BUENAVISTA";
		        case "124": return "CAIMITO";
		        case "204": return "COLOSO";
		        case "215": return "COROZAL";
		        case "221": return "Coveñas";
		        case "230": return "CHALAN";
		        case "233": return "El Roble";
		        case "235": return "GALERAS";
		        case "265": return "GUARANDA";
		        case "400": return "LA UNION";
		        case "418": return "LOS PALMITOS";
		        case "429": return "MAJAGUAL";
		        case "473": return "MORROA";
		        case "508": return "OVEJAS";
		        case "523": return "PALMITO";
		        case "670": return "Sampués";
		        case "678": return "SAN BENITO ABAD";
		        case "702": return "SAN JUAN BETULIA";
		        case "708": return "SAN MARCOS";
		        case "713": return "SAN ONOFRE";
		        case "717": return "SAN PEDRO";
		        case "742": return "SINCE";
		        case "771": return "SUCRE";
		        case "820": return "TOLU";
		        case "823": return "TOLUVIEJO";
		        default: return ciudad;
	    	  }

	    case "73":
	    	switch (ciudad) {
		      case "1": return "IBAGUE";
		      case "24": return "ALPUJARRA";
		      case "26": return "ALVARADO";
		      case "30": return "AMBALEMA";
		      case "43": return "ANZOATEGUI";
		      case "55": return "ARMERO";
		      case "67": return "ATACO";
		      case "124": return "CAJAMARCA";
		      case "148": return "CARMEN APICALA";
		      case "152": return "CASABIANCA";
		      case "168": return "CHAPARRAL";
		      case "200": return "COELLO";
		      case "217": return "COYAIMA";
		      case "226": return "CUNDAY";
		      case "236": return "DOLORES";
		      case "268": return "ESPINAL";
		      case "270": return "FALAN";
		      case "275": return "FLANDES";
		      case "283": return "FRESNO";
		      case "319": return "GUAMO";
		      case "347": return "HERVEO";
		      case "349": return "HONDA";
		      case "352": return "ICONONZO";
		      case "408": return "LERIDA";
		      case "411": return "LIBANO";
		      case "443": return "MARIQUITA";
		      case "449": return "MELGAR";
		      case "461": return "MURILLO";
		      case "483": return "NATAGAIMA";
		      case "504": return "ORTEGA";
		      case "520": return "PALOCABILDO";
		      case "547": return "PIEDRAS";
		      case "555": return "PLANADAS";
		      case "563": return "PRADO";
		      case "585": return "PURIFICACION";
		      case "616": return "RIOBLANCO";
		      case "622": return "RONCESVALLES";
		      case "624": return "ROVIRA";
		      case "671": return "SALDAÑA";
		      case "675": return "SAN ANTONIO";
		      case "678": return "SAN LUIS";
		      case "686": return "SANTA ISABEL";
		      case "770": return "SUAREZ";
		      case "854": return "VALLE DE SAN JUAN";
		      case "861": return "VENADILLO";
		      case "870": return "VILLAHERMOSA";
		      case "873": return "Villarrica";
		      default: return ciudad;
	    	}

	    case "76":
	    	switch (ciudad) {
		      case "1": return "CALI";
		      case "20": return "ALCALA";
		      case "36": return "ANDALUCIA";
		      case "41": return "ANSERMANUEVO";
		      case "54": return "ARGELIA";
		      case "100": return "BOLIVAR";
		      case "109": return "BUENAVENTURA";
		      case "111": return "BUGA";
		      case "113": return "BUGALAGRANDE";
		      case "122": return "CAICEDONIA";
		      case "126": return "CALIMA";
		      case "130": return "CANDELARIA";
		      case "147": return "CARTAGO";
		      case "233": return "DAGUA";
		      case "243": return "EL AGUILA";
		      case "246": return "EL CAIRO";
		      case "248": return "EL CERRITO";
		      case "250": return "EL DOVIO";
		      case "275": return "FLORIDA";
		      case "306": return "GINEBRA";
		      case "318": return "GUACARI";
		      case "364": return "JAMUNDI";
		      case "377": return "LA CUMBRE";
		      case "400": return "LA UNION";
		      case "403": return "LA VICTORIA";
		      case "497": return "OBANDO";
		      case "520": return "PALMIRA";
		      case "563": return "PRADERA";
		      case "606": return "RESTREPO";
		      case "616": return "RIOFRIO";
		      case "622": return "ROLDANILLO";
		      case "670": return "SAN PEDRO";
		      case "736": return "SEVILLA";
		      case "823": return "TORO";
		      case "828": return "TRUJILLO";
		      case "834": return "TULUA";
		      case "845": return "ULLOA";
		      case "863": return "VERSALLES";
		      case "869": return "VIJES";
		      case "890": return "YOTOCO";
		      case "892": return "YUMBO";
		      case "895": return "ZARZAL";
		      default: return ciudad;
	    	}
	    case "81":
	    	switch (ciudad) {
		      case "1": return "ARAUCA";
		      case "65": return "ARAUQUITA";
		      case "220": return "CRAVO NORTE";
		      case "300": return "FORTUL";
		      case "591": return "PUERTO RONDON";
		      case "736": return "SARAVENA";
		      case "794": return "TAME";
		      default: return ciudad;
	    	}
	    case "85":
	    	switch (ciudad) {
		      case "1": return "YOPAL";
		      case "10": return "AGUAZUL";
		      case "15": return "CHAMEZA";
		      case "125": return "HATO COROZAL";
		      case "136": return "LA SALINA";
		      case "139": return "MANI";
		      case "162": return "MONTERREY";
		      case "225": return "NUNCHIA";
		      case "230": return "OROCUE";
		      case "250": return "PAZ DE ARIPORO";
		      case "263": return "PORE";
		      case "279": return "RECETOR";
		      case "300": return "SABANALARGA";
		      case "315": return "SACAMA";
		      case "325": return "SAN LUIS DE PALENQUE";
		      case "400": return "TAMARA";
		      case "410": return "TAURAMENA";
		      case "430": return "TRINIDAD";
		      case "440": return "VILLANUEVA";
		      default: return ciudad;
	    	}
	    case "86":
	    	switch (ciudad) {
		      case "1": return "MOCOA";
		      case "219": return "COLON";
		      case "320": return "ORITO";
		      case "568": return "PUERTO ASIS";
		      case "569": return "PUERTO CAICEDO";
		      case "571": return "PUERTO GUZMAN";
		      case "573": return "PUERTO LEGUIZAMO";
		      case "749": return "SIBUNDOY";
		      case "755": return "SAN FRANCISCO";
		      case "757": return "LA DORADA";
		      case "760": return "SANTIAGO";
		      case "865": return "VALLE DEL GUAMEZ";
		      case "885": return "VILLAGARZON";
		      default: return ciudad;
		    	}

	    case "88":
	    	switch (ciudad) {
		      case "1": return "SAN ANDRES";
		      case "564": return "PROVIDENCIA";
		      default: return ciudad;
	    	}

	    case "91":
	    	switch (ciudad) {
		      case "1": return "LETICIA";
		      case "263": return "EL ENCANTO";
		      case "405": return "LA CHORRERA";
		      case "407": return "LA PEDRERA";
		      case "430": return "La Victoria";
		      case "460": return "MIRITI-PARANA";
		      case "530": return "Puerto Alegría";
		      case "536": return "Puerto Arica";
		      case "540": return "PUERTO NARIÑO";
		      case "669": return "PUERTO SANTANDER";
		      case "798": return "TARAPACA";
		      default: return ciudad;
	    	}

	    case "94":
	    	switch (ciudad) {
		      case "1": return "INIRIDA";
		      case "343": return "BARRANCO MINA";
		      case "663": return "Mapiripana";
		      case "883": return "SAN FELIPE";
		      case "884": return "PUERTO COLOMBIA";
		      case "885": return "LA GUADALUPE";
		      case "886": return "CACAHUAL";
		      case "887": return "PANA PANA";
		      case "888": return "MORICHAL NUEVO";
		      default: return ciudad;
	    	}

	    case "9":
	    	switch (ciudad) {
		      case "1": return "SAN JOSE DEL GUAVIARE";
		      case "15": return "CALAMAR";
		      case "25": return "EL RETORNO";
		      case "200": return "MIRAFLORES";
		      default: return ciudad;
	    	}

	    case "97":
	    	switch (ciudad) {
		      case "1": return "MITU";
		      case "161": return "CARURU";
		      case "511": return "PACOA";
		      case "666": return "TARAIRA";
		      case "777": return "PAPUNAHUA";
		      case "889": return "YAVARETE";
		      default: return ciudad;
	    	}

	    case "99":
	    	switch (ciudad) {
		      case "1": return "PUERTO CARREÑO";
		      case "524": return "LA PRIMAVERA";
		      case "624": return "SANTA ROSALIA";
		      case "773": return "CUMARIBO";
		      default: return ciudad;
	    	}

	    default: return ciudad;
    }
	}
}

