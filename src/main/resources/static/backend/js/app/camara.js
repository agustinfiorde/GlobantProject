
  // The width and height of the captured foto. We will set the
  // width to the value defined here, but the height will be
  // calculated based on the aspect ratio of the input stream.

  var width = 320;    // We will scale the foto width to this
  var height = 0;     // This will be computed based on the input stream

  // |streaming| indicates whether or not we're currently streaming
  // video from the camera. Obviously, we start at false.

  var streaming = false;

  // The various HTML elements we need to configure or control. These
  // will be set by the startup() function.

  var video = null;
  var canvas = null;
  var foto = null;
  var startbutton = null;

  function startup() {
    video = document.getElementById('video');
    canvas = document.getElementById('canvas');
    foto = document.getElementById('foto');
    imagen = document.getElementById('imagen');
    startbutton = document.getElementById('startbutton');

    navigator.mediaDevices.getUserMedia({ video: true, audio: false })
    .then(stream => video.srcObject = stream)
    .catch(e => log(e.name + ": "+ e.message));
    
    var log = msg => div.innerHTML += msg + "<br>";

    video.addEventListener('canplay', function(ev){
      if (!streaming) {
        height = video.videoHeight / (video.videoWidth/width);
      
        // Firefox currently has a bug where the height can't be read from
        // the video, so we will make assumptions if this happens.
      
        if (isNaN(height)) {
          height = width / (4/3);
        }
      
        video.setAttribute('width', width);
        video.setAttribute('height', height);
        canvas.setAttribute('width', width);
        canvas.setAttribute('height', height);
        streaming = true;
      }
    }, false);

    startbutton.addEventListener('click', function(ev){
      takepicture();
      ev.preventDefault();
    }, false);
    
    clearfoto();
  }

  // Fill the foto with an indication that none has been
  // captured.

  function clearfoto() {
    var context = canvas.getContext('2d');
    context.fillStyle = "#AAA";
    context.fillRect(0, 0, canvas.width, canvas.height);

    var data = canvas.toDataURL('image/png');
    foto.setAttribute('src', data);
  }
  
  // Capture a foto by fetching the current contents of the video
  // and drawing it into a canvas, then converting that to a PNG
  // format data URL. By drawing it on an offscreen canvas and then
  // drawing that to the screen, we can change its size and/or apply
  // other changes before drawing it.
  
  function decode(utftext) {
	  
	  	utftext = utftext.split(",")[1]
	    var string = "";
	    var i = 0;
	    var c = 0, c1 = 0, c2 = 0;

	    while ( i < utftext.length ) {

	        c = utftext.charCodeAt(i);

	        if (c < 128) {
	            string += String.fromCharCode(c);
	            i++;
	        }
	        else if((c > 191) && (c < 224)) {
	            c1 = utftext.charCodeAt(i+1);
	            string += String.fromCharCode(((c & 31) << 6) | (c1 & 63));
	            i += 2;
	        }
	        else {
	            c1 = utftext.charCodeAt(i+1);
	            c2 = utftext.charCodeAt(i+2);
	            string += String.fromCharCode(((c & 15) << 12) | ((c1 & 63) << 6) | (c2 & 63));
	            i += 3;
	        }

	    }
	    return string;
	}

  function takepicture() {
    var context = canvas.getContext('2d');
    if (width && height) {
      canvas.width = width;
      canvas.height = height;
      context.drawImage(video, 0, 0, width, height);
    
      var data = canvas.toDataURL('image/png');
      foto.setAttribute('src', data);
      var byte = decode(data);
      alert(byte);
      imagen.value= byte;
    } else {
      clearfoto();
    }
  }

  // Set up our event listener to run the startup process
  // once loading is complete.
 
  

function encenderCamara(){
	 window.addEventListener('load', startup(), false);
	 
}

function apagarCamara(){
	window.location.reload(false);
}

function cambiarFoto(event) {
  var imagen = event.target.files[0];
  var reader = new FileReader();

  var imgPerfil = document.getElementById("imagenPerfil");
  var inputPerfil = document.getElementById("file-5");
  
  imgPerfil.title = imagen.name;

  reader.onload = function(event) {
    imgPerfil.src = event.target.result;
  };

  reader.readAsDataURL(imagen);
  imgPerfil.setAttribute('width', "230px");
  imgPerfil.setAttribute('height', "230px");
  
  inputPerfil.setAttribute('width', "230px");
  inputPerfil.setAttribute('height', "230px");
	
}


