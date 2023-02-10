import 'videoplayer_platform_interface.dart';

class Videoplayer {
  Future<String?> openplayer({required String url}) {
    return VideoplayerPlatform.instance.getPlatformVersion(url: url);
  }
}
