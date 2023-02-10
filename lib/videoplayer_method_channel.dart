import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'videoplayer_platform_interface.dart';

/// An implementation of [VideoplayerPlatform] that uses method channels.
class MethodChannelVideoplayer extends VideoplayerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('videoplayer');

  @override
  Future<String?> getPlatformVersion({required String url}) async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion',{"url": url});
    return version;
  }
}
