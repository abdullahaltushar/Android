

import 'dart:io';

import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/material.dart';
import 'package:tflite/tflite.dart';
import 'package:image_picker/image_picker.dart';

class Home extends StatefulWidget {
  Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  bool _loading = true;
  late File _image;
  late List _output;
  final picker =ImagePicker();

  @override
void initState(){
  super.initState();
  loadModel().then((value){
    setState(() {
    });

  }
  );
}


  classifyImage(File image) async{

    var output= await Tflite.runModelOnImage(path: image.path,numResults: 2,threshold: 0.5,imageMean: 127.5, imageStd: 127.5);
    setState(() {
      _output= output!;
      _loading= false;
    });

  }

  loadModel() async{
    await Tflite.loadModel(model: 'assets/file/model_unquant.tflite',labels: 'assets/file/labels.txt');

  }
  @override
  void dispose() {
    // TODO: implement dispose
    Tflite.close();
    super.dispose();
  }
  pickImage() async{

    var image = await picker.getImage(source: ImageSource.camera);
    if (image== null) return null;
    setState(() {
      _image= File(image.path);
    });
    classifyImage(_image);
  }

  pickGallery() async{

    var image = await picker.getImage(source: ImageSource.gallery);
    if (image== null) return null;
    setState(() {
      _image= File(image.path);
    });
    classifyImage(_image);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFF101010),
      body: Container(
        padding: EdgeInsets.symmetric(horizontal: 24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            SizedBox(height:50),
            Text(
              'TeachableMachine.com CNN',
              style: TextStyle(color: Color(0xFFEEDA28),
            ),
            ),
            SizedBox(height: 6,),
            Text("Detect Dogs and cats", style: TextStyle(color: Color(0xFFE99600),fontWeight: FontWeight.w500,fontSize: 28),
            ),
            SizedBox(height: 40),
            Center(child: _loading? Container(
              width: 280,
              child: Column(children: <Widget>[
                Image.asset('assets/images/cat.PNG'),
                SizedBox(height: 50),
              ]),
            ) : Container(

              child: Column(children: <Widget>[
                Container(
                  height: 250,
                  child: Image.file(_image),
                ),
                SizedBox(height: 20),
                _output != null ? Text('${_output[0]}', 
                style: 
                TextStyle(
                  color: Colors.white,
                  fontSize: 20
                  ),
                  ) : Container()
              ],
              
              ),
            ),
            ),
            Container(
              width: MediaQuery.of(context).size.width,
              child: Column(
                children: <Widget>[
                  GestureDetector(onTap: pickImage,
                  child: Container(width: 
                  MediaQuery.of(context).size.width -150,
                  alignment: Alignment.center,
                  padding:EdgeInsets.symmetric(horizontal: 24, vertical:17),
                  decoration: BoxDecoration(color: Color(0xFFE99600),borderRadius: BorderRadius.circular(6),
                  ),
                  child: Text("Take a photo",style:TextStyle(color: Colors.white) ,),
                  ),
                  ),
                  SizedBox(height: 10,),
                   GestureDetector(onTap: pickGallery,
                  child: Container(width: 
                  MediaQuery.of(context).size.width -150,
                  alignment: Alignment.center,
                  padding:EdgeInsets.symmetric(horizontal: 24, vertical:17),
                  decoration: BoxDecoration(color: Color(0xFFE99600),borderRadius: BorderRadius.circular(6),
                  ),
                  child: Text("Camera Roll",
                  style:TextStyle(color: Colors.white) ,),
                  ),
                  ),


                ],
              ),
            )




          ],
        ),
      ),
    );
  }
}