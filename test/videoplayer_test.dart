import 'package:flutter_test/flutter_test.dart';
import 'package:videoplayer/videoplayer.dart';
import 'package:videoplayer/videoplayer_platform_interface.dart';
import 'package:videoplayer/videoplayer_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockVideoplayerPlatform
    with MockPlatformInterfaceMixin
    implements VideoplayerPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final VideoplayerPlatform initialPlatform = VideoplayerPlatform.instance;

  test('$MethodChannelVideoplayer is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelVideoplayer>());
  });

  test('getPlatformVersion', () async {
    Videoplayer videoplayerPlugin = Videoplayer();
    MockVideoplayerPlatform fakePlatform = MockVideoplayerPlatform();
    VideoplayerPlatform.instance = fakePlatform;

    expect(await videoplayerPlugin.openplayer(), '42');
  });
}
