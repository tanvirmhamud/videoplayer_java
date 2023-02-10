import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:videoplayer/videoplayer_method_channel.dart';

void main() {
  MethodChannelVideoplayer platform = MethodChannelVideoplayer();
  const MethodChannel channel = MethodChannel('videoplayer');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
